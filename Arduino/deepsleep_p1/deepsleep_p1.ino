
//#include <ESP8266WiFi.h>
//
//const int SLEEP_TIME = 1800; // sleep time in seconds (1800 seconds = 30 minutes)
//
//void setup() {
//  Serial.begin(115200); // start serial communication
//}
//
//void loop() {
//  Serial.println("Hello, this message is printed once every 30 minutes.");
//
//  WiFi.forceSleepBegin(); // put device in light sleep mode
//  delay(SLEEP_TIME * 1000);
//  WiFi.forceSleepWake();
//}
#include <Adafruit_SleepyDog.h>
const int SLEEP_TIME = 1800; // sleep time in seconds (1800 seconds = 30 minutes)

void setup() {
  Serial.begin(9600); // start serial communication
 // SleepyDog.watchdogSetup();
}

void loop() {
  Serial.println("Hello, this message is printed once every 3 sec.");

 int sleepMS = Watchdog.sleep(1000);

 Serial.println("delay done");
 #if defined(USBCON) && !defined(USE_TINYUSB)
  USBDevice.attach();
#endif
}
