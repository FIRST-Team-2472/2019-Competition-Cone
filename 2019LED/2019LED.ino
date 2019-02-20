// color swirl! connect an RGB LED to the PWM pins as indicated
// in the #defines
// public domain, enjoy!
 
#define REDPIN 5
#define GREENPIN 6
#define BLUEPIN 3
 
#define WaitTime 100     // make this higher to slow down
 
void setup() {
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);
}
 
 
void loop() {
  int r, g, b;
 
 analogWrite(REDPIN, 0);
 delay(WaitTime);
 analogWrite(REDPIN, 255);
 analogWrite(GREENPIN, 0);
 delay(WaitTime);
 analogWrite(GREENPIN, 255);
  analogWrite(BLUEPIN, 0);
 delay(WaitTime);
 analogWrite(BLUEPIN, 255);
  } 
  
