#include <ZumoBuzzer.h>
#include <ZumoMotors.h>
#include <Pushbutton.h>
#include <QTRSensors.h>
#include <ZumoReflectanceSensorArray.h>
#include <NewPing.h>

#define LED 13

// this might need to be tuned for different lighting conditions, surfaces, etc.
#define TRIGGER_PIN       12  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN          11  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define LED_PIN           13
#define MAX_DISTANCE      200 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.
#define ABOVE_LINE(sensor)((sensor) > QTR_THRESHOLD) //For checking if the reflectance sensor is on the black line

#define QTR_THRESHOLD  1500 // microseconds
// these might need to be tuned for different motor types
#define REVERSE_SPEED     100 // 0 is stopped, 400 is full speed
#define TURN_SPEED        100
#define FORWARD_SPEED     100
#define REVERSE_DURATION  200 // ms
#define TURN_DURATION     300 // ms


ZumoBuzzer buzzer;
ZumoMotors motors;
Pushbutton button(ZUMO_BUTTON); // pushbutton on pin 12

#define NUM_SENSORS 6
unsigned int sensor_values[NUM_SENSORS];
ZumoReflectanceSensorArray sensors(QTR_NO_EMITTER_PIN);

boolean hitLeft;        //Check if the sensor hit left wall
boolean hitRight;       //Check if the sensor hit right wall

char dir;               //To be used in switch statement for processing the input command
boolean resumed = false;//To be used for determine if we are controlling robot or not


void setup()
{
  // uncomment if necessary to correct motor directions
  //motors.flipLeftMotor(true);
  //motors.flipRightMotor(true);

  //Initialise variables

  hitLeft = false;
  hitRight = false;

  Serial.begin(57600);  //Set baud rate

  Serial.println("Press Zumo button to begin");
  button.waitForButton();
  buzzer.playNote(NOTE_G(3), 200, 15);
  Serial.println("Calibrating");
  void calibrateSensorArray();
  Serial.println("Calibration completed");
  Serial.println("Press w to start");
  pinMode(LED, HIGH);
}

void calibrateSensorArray ()
{
  sensors.calibrate();
}

void keepInCorridor()//Keep with black lines
{

  sensors.read(sensor_values);
  //If left sensor as well right sensor are on the black line the it means its a wall
  //If there was a line on the left immidiatly after the line on the righ tor vice verse
  //then it means its a corner
  if ((hitLeft && hitRight) || (ABOVE_LINE(sensor_values[0]) && ABOVE_LINE(sensor_values[5])))
  {
    motors.setSpeeds(-REVERSE_SPEED, -REVERSE_SPEED);
    Serial.write("FRONT WALL");
    delay(300);
    stop();
  } else
  {
    if (ABOVE_LINE(sensor_values[0]))      // if left sensor detects line, reverse and turn to the right
    {
      Serial.println("LEFT WALL");
      motors.setSpeeds(-REVERSE_SPEED, -REVERSE_SPEED);
      delay(REVERSE_DURATION);
      motors.setSpeeds(TURN_SPEED, -TURN_SPEED);
      delay(TURN_DURATION);
      if (hitLeft)
        hitLeft = false;
      else hitLeft = true;
    }
    else if (ABOVE_LINE(sensor_values[5]))  // if right sensor detects line, reverse and turn to the left
    {
      Serial.println("RIGHT WALL");
      motors.setSpeeds(-REVERSE_SPEED, -REVERSE_SPEED);
      delay(REVERSE_DURATION);
      motors.setSpeeds(-TURN_SPEED, TURN_SPEED);
      delay(TURN_DURATION);
      motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);
      if (hitRight)
        hitRight = false;
      else hitRight = true;
    }
    else
    {
      // otherwise, go straight
      motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);
    }
  }
}

void stop() {
  motors.setSpeeds(0, 0);
  resumed = false;
  Serial.println("Stopping");
}


void processCommand(char dir)
{
  switch (dir)
  {
    case 'w': {
        //Forward
        hitLeft = false;
        hitRight = false;
        resumed = true;
        keepInCorridor();
      } break;
    case 'c': {       //Continue
        //Reset left and right wall hits to allow control
        hitLeft = false;
        hitRight = false;
        resumed = true;
        keepInCorridor();
      } break;
    case 'a': {       //Left
        motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);

      } break;
    case 's': {       //Back
        motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
        delay(2);
      } break;
    case 'd': {       //Right
        motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
        delay(2);
      } break;
    case 'p': {       //Pause
        stop();
        delay(2);
      } break;
 
    default: Serial.println("Invalid command");
  }
}


void loop()
{

  if (Serial.available() > 0) {
    dir = Serial.read();
    Serial.print(dir);
    Serial.println(" was pressed");
    processCommand(dir);
  }
  else {
    if (resumed) keepInCorridor();//We are not controlling it
  }
  delay(10);

}

