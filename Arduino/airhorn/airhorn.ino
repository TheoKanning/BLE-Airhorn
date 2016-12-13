const int maxVolume = 100;
const float weight = 0.1f;
const int period = 20; //50Hz

const int buttonPin = 2; //pick the correct pin later

float volume = 0;

void setup() {
  pinMode(buttonPin, INPUT);
  digitalWrite(buttonPin, HIGH); // attach pull-up resistor
  Serial.begin(115200);
}

void loop() {
  updateVolume();
  Serial.write(round(volume));
  delay(20);
}

void updateVolume(){
  int input;
  // button pressed -> pin low
  if(digitalRead(buttonPin) == LOW) {
    input = maxVolume;
  } else {
    input = 0;
  }

  volume = volume + (input - volume)*weight;
}

