#include <MQ135.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>


#define DHTPIN D2
#define DHTTYPE DHT11
DHT_Unified dht(DHTPIN, DHTTYPE);


#define SERVER_IP "http://krystofcejchan.cz/arduino_aiq_quality/measurement.php"


#ifndef STASSID
#define STASSID "xxx"
#define STAPSK  "yyy"
#endif

#define MQ135_PIN A0
MQ135 mq135_sensor = MQ135(MQ135_PIN);

#define sizeofppms 20
#define sizeofisConfs 19


float ppms[sizeofppms] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
boolean isConfs[sizeofisConfs] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
int loop_counter = 0;
float conf_limit = 5;
boolean isConf = false;
int chk;
int sleep_ms = 10000;

unsigned long lastMillis = 0;
boolean first = true;
float hum = 55;
float temp = 20;
float correctedPPM;


void setup() {
  Serial.begin(115200);

  dht.begin();
  Serial.println(F("DHTxx Unified Sensor Example"));
  // Print temperature sensor details.
  sensor_t sensor;
  dht.temperature().getSensor(&sensor);
  Serial.println(F("------------------------------------"));
  Serial.println(F("Temperature Sensor"));
  Serial.print  (F("Sensor Type: ")); Serial.println(sensor.name);
  Serial.print  (F("Driver Ver:  ")); Serial.println(sensor.version);
  Serial.print  (F("Unique ID:   ")); Serial.println(sensor.sensor_id);
  Serial.print  (F("Max Value:   ")); Serial.print(sensor.max_value); Serial.println(F("°C"));
  Serial.print  (F("Min Value:   ")); Serial.print(sensor.min_value); Serial.println(F("°C"));
  Serial.print  (F("Resolution:  ")); Serial.print(sensor.resolution); Serial.println(F("°C"));
  Serial.println(F("------------------------------------"));
  // Print humidity sensor details.
  dht.humidity().getSensor(&sensor);
  Serial.println(F("Humidity Sensor"));
  Serial.print  (F("Sensor Type: ")); Serial.println(sensor.name);
  Serial.print  (F("Driver Ver:  ")); Serial.println(sensor.version);
  Serial.print  (F("Unique ID:   ")); Serial.println(sensor.sensor_id);
  Serial.print  (F("Max Value:   ")); Serial.print(sensor.max_value); Serial.println(F("%"));
  Serial.print  (F("Min Value:   ")); Serial.print(sensor.min_value); Serial.println(F("%"));
  Serial.print  (F("Resolution:  ")); Serial.print(sensor.resolution); Serial.println(F("%"));
  Serial.println(F("------------------------------------"));

  WiFi.begin(STASSID, STAPSK);
  unsigned int stat_counter = 0;
  while (WiFi.status() != WL_CONNECTED) {
    if (stat_counter % 10 == 0 )
      Serial.println(wl_status_to_string(WiFi.status()));
    else
      Serial.print("!");

    stat_counter += 1;
    delay(500 * 2);
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  calcTempAndHum();

  float correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);

  if (not isConf) {
    isConfigurated(correctedPPM);
    Serial.print((temp));
    Serial.println("°C");
    delay(333);
    return; //starts the loop() method from the beggining
  }

  if (first || millis() - lastMillis > sleep_ms) {
    first = false;
    if ((WiFi.status() == WL_CONNECTED)) {

      calcTempAndHum();

      Serial.println(String(temp) + "°C;\t" + String(hum) + "%");

      correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);

      WiFiClient client;
      HTTPClient http;

      Serial.print("[HTTP] begin...\n");
      // configure traged server and url
      http.begin(client, SERVER_IP); //HTTP
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");

      Serial.print("[HTTP] POST...\n");
      // start connection and send HTTP header and body
      int httpCode = http.POST("air_q=" + String(correctedPPM) + "&hum=" + String(hum) + "&temp=" + String(temp));


      // httpCode will be negative on error
      if (httpCode > 0) {
        // HTTP header has been send and Server response header has been handled
        Serial.printf("[HTTP] POST... code: %d\n", httpCode);

        // file found at server
        if (httpCode == HTTP_CODE_OK) {
          const String& payload = http.getString();
          Serial.println("received payload:\n<<");
          Serial.println(payload);
          Serial.println(">>");
        }
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }

      http.end();
    } lastMillis = millis();
  }
  else {
    calcTempAndHum();
    float rzero = mq135_sensor.getRZero();
    float correctedRZero = mq135_sensor.getCorrectedRZero(temp, hum);
    float resistance = mq135_sensor.getResistance();
    float ppm = mq135_sensor.getPPM();
    float correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);

    Serial.print("MQ135 RZero: ");
    Serial.print(rzero);
    Serial.print("\t Corrected RZero: ");
    Serial.print(correctedRZero);
    Serial.print("\t Resistance: ");
    Serial.print(resistance);
    Serial.print("\t PPM: ");
    Serial.print(ppm);
    Serial.print("\t Corrected PPM: ");
    Serial.print(correctedPPM);
    Serial.println("ppm");
  }

}


boolean isConfigurated(float ppm) {
  Serial.println("CONFIGURATING " + String(ppm));
  Serial.println(String(loop_counter) + " " + String(sizeofppms - 1));
  ppms[loop_counter] = ppm;
  if (loop_counter < sizeofppms - 1)
    loop_counter = loop_counter + 1;
  else loop_counter = 0;

  for (int i = 0; i < sizeofppms; i++)
    isConfs[i] = isCloseTo(ppms[i]);

  return isConfsTrue();
}

boolean isCloseTo(float ppm_) {
  for (int i = 0; i < sizeofppms; i++) {
    if (abs(ppm_ - ppms[i]) >= conf_limit)
      return false;
  }
  return true;
}

boolean isConfsTrue() {
  for (int j = 0; j < sizeofisConfs; j++) {
    if (not isConfs[j])
      return false;
  }

  isConf = true;
  Serial.println("Conf. 100% done...");
  sleep_ms = (int)(10000/*00*/); //(sleep * 20.6);
  return true;

}

void calcTempAndHum() {
  sensors_event_t event;
  dht.temperature().getEvent(&event);
  if (not isnan(event.temperature)) {
    temp = event.temperature;
  }
  dht.humidity().getEvent(&event);
  if (not isnan(event.relative_humidity)) {
    hum = event.relative_humidity;
  }
}

const char* wl_status_to_string(wl_status_t status) {
  switch (status) {
    case WL_NO_SHIELD: return "WL_NO_SHIELD";
    case WL_IDLE_STATUS: return "WL_IDLE_STATUS";
    case WL_NO_SSID_AVAIL: return "WL_NO_SSID_AVAIL";
    case WL_SCAN_COMPLETED: return "WL_SCAN_COMPLETED";
    case WL_CONNECTED: return "WL_CONNECTED";
    case WL_CONNECT_FAILED: return "WL_CONNECT_FAILED";
    case WL_CONNECTION_LOST: return "WL_CONNECTION_LOST";
    case WL_DISCONNECTED: return "WL_DISCONNECTED";
  }
}



//https://www.makerguides.com/air-pollution-monitoring-and-alert-system-using-arduino-and-mq135/#:~:text=Air%20Quality%20Index%20(AQI)%20Values,Hazardous
//https://www.elprocus.com/mq135-air-quality-sensor/#:~:text=Preheating%20of%2020%20seconds%20is%20required%20before%20the%20operation%2C%20to%20obtain%20the%20accurate%20output.