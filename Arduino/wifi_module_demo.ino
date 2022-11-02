#include <SoftwareSerial.h>

#ifndef HAVE_HWEspSerial
#include "SoftwareSerial.h"
SoftwareSerial EspSerial(2, 3); // RX, TX
#endif

void setup() {
  Serial.begin(115200);
  delay(2000);
  EspSerial.begin(115200); // your esp's baud rate might be different 9600, 57600, 76800 or 115200
  delay(2000);
  Serial.println("Trying to send AT+GMR ...");
  EspSerial.println("AT+GMR");
}

void loop() {
  if(EspSerial.available()) // check if the ESP module is sending a message
  {
    while(EspSerial.available())
    {
      // The esp has data so display its output to the serial window
      char c = EspSerial.read(); // read the next character.
      Serial.write(c);
    }
  }

  if(Serial.available()) // check if connection through Serial Monitor from computer is available
  {
    // the following delay is required because otherwise the arduino will read the first letter of the command but not the rest
    // In other words without the delay if you use AT+RST, for example, the Arduino will read the letter A send it, then read the rest and send it
    // but we want to send everything at the same time.
    delay(1000);

    String command="";

    while(Serial.available()) // read the command character by character
    {
        // read one character
      command+=(char)Serial.read();
    }
    EspSerial.println(command); // send the read character to the Esp module
  }
}