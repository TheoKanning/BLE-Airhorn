const int maxVolume = 255;
const float weight = 0.1f;
const int period = 20; //50Hz

const int buttonPin = 5; //pick the correct pin later

int volume = 0;

void setup() {
  pinMode(buttonPin, INPUT);
  Serial.begin(115200);
}

void loop() {
  updateVolume();
  Serial.println(volume, DEC);
  delay(20);
}

void updateVolume(){
  int input;
  if(digitalRead(buttonPin) == HIGH) {
    input = maxVolume;
  } else {
    input = 0;
  }

  volume += round((input - volume)/weight);
  
}

