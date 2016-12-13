const int maxVolume = 100;
const float weight = 0.5f;
const int period = 20; //50Hz

const int buttonPin = 2; //pick the correct pin later

float volume = 0;
int oldVolumeOutput = -1;

void setup() {
  pinMode(buttonPin, INPUT);
  digitalWrite(buttonPin, HIGH); // attach pull-up resistor
  Serial.begin(115200);
}

void loop() {
  updateVolume();
  delay(20);
}

void updateVolume() {
  int input;
  // button pressed -> pin low
  if(digitalRead(buttonPin) == LOW) {
    input = maxVolume;
  } else {
    input = 0;
  }

  volume = volume + (input - volume)*weight;
  int volumeOutput = round(volume);

  if(volumeOutput != oldVolumeOutput){
    Serial.write(volumeOutput);
  }

  oldVolumeOutput = volumeOutput;
}

