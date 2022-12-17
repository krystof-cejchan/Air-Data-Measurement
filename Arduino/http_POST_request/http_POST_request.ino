#include <MQ135.h>
#include <DHT.h>


#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

#define DHTPIN 7
#define DHTTYPE DHT22
DHT dht(DHTPIN, DHTTYPE);

#define SERVER_IP "http://krystofcejchan.cz/arduino_aiq_quality/measurement.php"


#ifndef STASSID
#define STASSID "x"
#define STAPSK  "y"
#endif

#define MQ135_PIN A0
MQ135 mq135_sensor = MQ135(MQ135_PIN);

unsigned long lastMillis = 0;
boolean first = true;
float hum;
float temp;
#define delayValue 60000


void setup() {

  Serial.begin(115200);

  Serial.println();
  Serial.println();
  Serial.println();

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
  // wait for WiFi connection
  if (first || millis() - lastMillis > delayValue) {
    first = false;
    if ((WiFi.status() == WL_CONNECTED)) {

     /* hum = dht.readHumidity();
      temp = dht.readTemperature();*/
      float correctedPPM = mq135_sensor.getCorrectedPPM(20, 70);
      Serial.println(correctedPPM);
      WiFiClient client;
      HTTPClient http;

      Serial.print("[HTTP] begin...\n");
      // configure traged server and url
      http.begin(client, SERVER_IP); //HTTP
      http.addHeader("Content-Type", "application/x-www-form-urlencoded");

      Serial.print("[HTTP] POST...\n");
      // start connection and send HTTP header and body
      int httpCode = http.POST("value=" + String(correctedPPM));


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

}