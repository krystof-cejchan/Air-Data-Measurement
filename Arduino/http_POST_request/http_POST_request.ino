#include <MQ135.h>
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>

#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

#define DHTPIN D2
#define DHTTYPE DHT22
DHT_Unified dht(DHTPIN, DHTTYPE);


#define SERVER_IP "http://krystofcejchan.cz/arduino_aiq_quality/measurement.php"


#ifndef STASSID
#define STASSID "x"
#define STAPSK  "y"
#endif

#define MQ135_PIN A0
MQ135 mq135_sensor = MQ135(MQ135_PIN);

unsigned long lastMillis = 0;
boolean first = true;
float hum = 55;
float temp = 20;
float correctedPPM;
#define delayValue 10000


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

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print('!');
  }
  Serial.println("");
  Serial.print("Connected! IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  //correctedPPM = mq135_sensor.getCorrectedPPM(-10, 80);
  // wait for WiFi connection
  if (first || millis() - lastMillis > delayValue) {
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
  }

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