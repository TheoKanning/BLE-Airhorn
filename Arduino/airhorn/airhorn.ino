const int buttonPin = 2;

int swagCount = 0;
boolean buttonHeld = false; // whether or not the button was previously held down

void setup() {
  pinMode(buttonPin, INPUT);
  digitalWrite(buttonPin, HIGH); // attach pull-up resistor
  Serial.begin(115200);
}

void loop() {
  updateSwagCount();
  delay(20);
}

void updateSwagCount() {
  int input;
  if (buttonPressed()) {
    if (!buttonHeld) {
      swagCount++;
      Serial.write(swagCount);
    }
    buttonHeld = true;
  } else {
    buttonHeld = false;
  }
}

/**
   Returns true if the button is currently being pressed, false otherwise
*/
boolean buttonPressed() {
  // button pressed -> pin low
  return digitalRead(buttonPin) == LOW;
}

