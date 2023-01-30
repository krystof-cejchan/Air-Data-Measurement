
#include <MQ135.h>

#include <Adafruit_Sensor.h>

#include <DHT.h>

#include <DHT_U.h>

#include <ESP8266WiFi.h>

#include <ESP8266HTTPClient.h>

#include <NTPClient.h>

#include <WiFiUdp.h>


#define DHTPIN D2
#define DHTTYPE DHT22

DHT_Unified dht(DHTPIN, DHTTYPE);

#define HOST "•••••••••••••••••••••••••••••••••••••"
#define SERVER_IP \
  "••••••••••••••••••••••••••••••••••••••••••••••••••"

//#define SERVER_IP
//"http://krystofcejchan.cz/arduino_aiq_quality/measurement.php"

#ifndef STASSID
#define STASSID "••••••••••••••••••••••••••••"
#define STAPSK "••••••••••••••••••••••••••••"
#endif

#define MQ135_PIN A0
MQ135 mq135_sensor = MQ135(MQ135_PIN);

// Define NTP Client to get time
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "pool.ntp.org", 1 * 60 * 60, 60000);

#define sizeofppms 20
#define sizeofisConfs 19

#define location_Id 5

const String scrtpsw = "";

float ppms[sizeofppms] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                         };
boolean isConfs[sizeofisConfs] = {
  false, false, false, false, false, false, false, false, false, false,
  false, false, false, false, false, false, false, false, false
};
int loop_counter = 0;
float conf_limit = 40.00;
boolean isConf = false;
int chk;
const unsigned int sleep_ms = 30 * 60000;

unsigned long lastMillis = 0;
boolean first = true;
float hum = 55;
float temp = 20;
float correctedPPM;

void setup() {
  Serial.begin(115200);
  dht.begin();
  timeClient.begin();


  sensor_t sensor;
  dht.temperature().getSensor(&sensor);
  WiFi.begin(STASSID, STAPSK);
  unsigned int stat_counter = 0;
  Serial.print("Connecting to " + String(STASSID) + "\t");
  while (WiFi.status() != WL_CONNECTED) {
    if (stat_counter % 10 == 0)
      Serial.println(wl_status_to_string(WiFi.status()));
    else
      Serial.print("#");

    stat_counter += 1;
    delay(500 * 2);
  }
  Serial.println("");
  Serial.print("Connected!IPaddress:");
  Serial.println(WiFi.localIP());
}

/**
    Loop method running endlessly until termination or error is thrown
*/
void loop() {
  //calcTempAndHum();

  //float correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);

  if (not isConf) {
    calcTempAndHum();
    isConfigurated(mq135_sensor.getCorrectedPPM(temp, hum));
    Serial.print((temp));
    Serial.println("°C");
    delay(200);
    return;  // starts the loop() method from the beggining
  }

  if (first || millis() - lastMillis > sleep_ms) {
    first = false;
    if ((WiFi.status() == WL_CONNECTED)) {
      calcTempAndHum();

      Serial.println(String(temp) + "°C;\t" + String(hum) + "%");

      correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);
      delay(300);
      String currDate = getCurrentDate(true);
      String json_post_data = generate_http_post_body(
                                (short)5, currDate, correctedPPM, temp, hum);

      WiFiClient client;
      HTTPClient http;

      http.setTimeout(20000);
      client.setTimeout(20000);
      /*Array
        (
        [Geoip-Country-Name] => Czech Republic
        [Geoip-Country-Code] => CZ
        [Geoip-Continent-Code] => EU
        [Geoip-Addr] => x.x.x.x.x.x.
        [Content-Length] => 32
        [Content-Type] => application/x-www-form-urlencoded
        [Connection] => close
        [Accept-Encoding] => identity;q=1,chunked;q=0.1,*;q=0
        [User-Agent] => ESP8266HTTPClient
        [Host] => krystofcejchan.cz
        )*/
      Serial.print("[HTTP]begin...\n");

      http.begin(client, SERVER_IP);
      http.addHeader("Content-Type", "application/json");
      http.addHeader("Accept-Charset", "utf-8");
      http.addHeader("Content-Length", String(json_post_data.length()));
      http.addHeader("Host", HOST);
      http.addHeader("User-Agent", "ESP8266HTTPClient");
      http.addHeader("Accept", "*/*");
      http.addHeader("Accept-Encoding", "gzip, deflate, br");
      http.addHeader("Connection", "keep-alive");
      http.addHeader("Authorization", String(currDate) + ";" + String(scrtpsw));
      Serial.println( String(currDate) + ";" + String(scrtpsw));
      Serial.print(currDate);

      int httpCode = http.POST(json_post_data);

      while (isnan(httpCode) or httpCode == 0) {
        Serial.print('0');
      }
      if (httpCode > 0) {
        Serial.printf("[HTTP]POST...code:%d\n", httpCode);

        if (httpCode == HTTP_CODE_OK) {
          const String& payload = http.getString();
          Serial.println("receivedpayload:\n<<");
          Serial.println(payload);
          Serial.println(">>");
          // Serial.println(generate_http_post_body(5, "time", 50, 40, 60));
        }
      } else
        Serial.printf("[HTTP]POST...failed, error: %s\n",
                      http.errorToString(httpCode).c_str());

      http.end();
    }
    lastMillis = millis();
  } else
    calcTempAndHum();
}

String getCurrentDate(boolean withTime) {
  timeClient.update();

  time_t epochTime = timeClient.getEpochTime();
  String formattedTime = timeClient.getFormattedTime();
  struct tm* ptm = gmtime((time_t*)&epochTime);

  String monthDay = String(ptm->tm_mday);
  String currentMonth = String(ptm->tm_mon + 1);
  String currentYear = String(ptm->tm_year + 1900);
  if (monthDay.length() == 1) {
    monthDay = '0' + monthDay;
  }
  if (currentMonth.length() == 1) {
    currentMonth = '0' + currentMonth;
  }
  String currentDate = monthDay + "." + currentMonth + "." + currentYear;

  const String date_and_time =
    currentDate + ' ' + (withTime ? formattedTime : "");

  return date_and_time;
}

boolean isConfigurated(float ppm) {
  Serial.println("CONFIGURATING \t" + String(ppm));
  Serial.println(String(loop_counter) + " / " + String(sizeofppms - 1));
  ppms[loop_counter] = ppm;
  if (loop_counter < sizeofppms - 1)
    loop_counter = loop_counter + 1;
  else
    loop_counter = 0;

  for (int i = 0; i < sizeofppms; i++) isConfs[i] = isCloseTo(ppms[i]);

  return isConfsTrue();
}

boolean isCloseTo(float ppm_) {
  for (int i = 0; i < sizeofppms; i++)
    if (ppms[i] == 0) return false;
  for (int i = 0; i < sizeofppms; i++)
    if (abs(ppm_ - ppms[i]) >= conf_limit) return false;

  return true;
}

boolean isConfsTrue() {
  for (int j = 0; j < sizeofisConfs; j++) {
    if (not isConfs[j]) return false;
  }
  isConf = true;
  Serial.println("Conf.100% done...");
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

  delay(500);
}

String generate_http_post_body(short locationId, String arduino_time,
                               double air_q, double temp, double hum) {
  // "{\"locationId\":{\"id\":\%i\},\"arduinoTime\":\"%s\",\"airQuality\":%d,\"temperature\":%d,\"humidity\":%d}";
  Serial.println(("{\"locationId\":{\"id\":" + String(locationId) +
                  "},\"arduinoTime\":\"" + arduino_time + "\",\"airQuality\":" +
                  String(air_q) + ",\"temperature\":" + String(temp) +
                  ",\"humidity\":" + String(hum) + "}"));
  return ("{\"locationId\":{\"id\":" + String(locationId) +
          "},\"arduinoTime\":\"" + arduino_time + "\",\"airQuality\":" +
          String(air_q) + ",\"temperature\":" + String(temp) +
          ",\"humidity\":" + String(hum) + "}");
}

const char* wl_status_to_string(wl_status_t status) {
  switch (status) {
    case WL_NO_SHIELD:
      return "WL_NO_SHIELD";
    case WL_IDLE_STATUS:
      return "WL_IDLE_STATUS";
    case WL_NO_SSID_AVAIL:
      return "WL_NO_SSID_AVAIL";
    case WL_SCAN_COMPLETED:
      return "WL_SCAN_COMPLETED";
    case WL_CONNECTED:
      return "WL_CONNECTED";
    case WL_CONNECT_FAILED:
      return "WL_CONNECT_FAILED";
    case WL_CONNECTION_LOST:
      return "WL_CONNECTION_LOST";
    case WL_DISCONNECTED:
      return "WL_DISCONNECTED";
  }
}

// https://www.makerguides.com/air-pollution-monitoring-and-alert-system-using-arduino-and-mq135/#:~:text=Air%20Quality%20Index%20(AQI)%20Values,Hazardous
// https://www.elprocus.com/mq135-air-quality-sensor/#:~:text=Preheating%20of%2020%20seconds%20is%20required%20before%20the%20operation%2C%20to%20obtain%20the%20accurate%20output.