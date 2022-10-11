#include <MQ135.h>
#include <DHT.h>

#define MQ135_PIN A5
#define DHTPIN 7
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);

MQ135 mq135_sensor = MQ135(MQ135_PIN);

float ppms[20] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
int sizeofppms = 20, sizeofisConfs = 19;
boolean isConfs[19] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
int loop_counter = 0;
float conf_limit = 0.25;
boolean isConf = false;
int chk;
float hum;
float temp;
int sleep = 1501;

//Location data set-up, varies with each measuring device
String location_name = "";//enum
String city = "";
String street = "";
String house_number = "";
//@Nullable
String room_identifier = "";
//@Nullable
double meters_above_ground = 0.0;

void setup() {
  Serial.begin(9600);
  pinMode(3, OUTPUT);
  dht.begin();
}

void loop() {

  hum = dht.readHumidity();
  temp = dht.readTemperature();

  float correctedPPM = mq135_sensor.getCorrectedPPM(temp, hum);

  if (not isConf) {
    isConfigurated(correctedPPM); Serial.print((temp));
    Serial.println("°C");
  }
  else {
    Serial.print((temp));
    Serial.print("°C\t");
    Serial.println(correctedPPM);
  }
  delay(sleep);
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
    if (abs(ppm_ - ppms[i]) > conf_limit)
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
  sleep = (int)(sleep * 6.6);
  return true;

}



//https://www.makerguides.com/air-pollution-monitoring-and-alert-system-using-arduino-and-mq135/#:~:text=Air%20Quality%20Index%20(AQI)%20Values,Hazardous