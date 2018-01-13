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

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
#define NUM_SENSORS 6
unsigned int sensor_values[NUM_SENSORS];
ZumoReflectanceSensorArray sensors(QTR_NO_EMITTER_PIN);

int navArrayCount;      //For keeping track of index for the nav data
int objectID;           //For keeping track of objects that are found in rooms
int corridorID;         //Counting different corridors
int roomID;             //Counting number of each room in each corridor
boolean hitLeft;        //Check if the sensor hit left wall
boolean hitRight;       //Check if the sensor hit right wall

boolean isRoom;         //To check if we are going to enter a room or corridor

struct nav {            //Recording of each step when going to corridor or room
  int corrID;           //and whether it was in the left or right
  int roomID;
  char roomCorr;
  char leftRight;
};

char dir;               //To be used in switch statement for processing the input command
boolean resumed = false;//To be used for determine if we are controlling robot or not
nav navData[20];        //Initialise array of nav. 20 items to begin with
//but can be increased with inline arrays

void setup()
{
  // uncomment if necessary to correct motor directions
  //motors.flipLeftMotor(true);
  //motors.flipRightMotor(true);

  //Initialise variables
  objectID = 0;
  corridorID = 0;
  roomID = 0;
  navArrayCount = 0;
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

void scanRoom() //For searching rooms
{
  boolean found = false;
  motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED); //Go in the room
  delay(1000);
  for (int i = 0; i < 10; i++)//rotate and scan.
  {
    if ((sonar.ping_cm() > 0 && sonar.ping_cm() < 10))
    {
      found = true;//check multiple times to check different areas of room
      buzzer.playNote(NOTE_G(3), 200, 15);
    }
    motors.setSpeeds(150, -150);
    delay(300);
  }
  if (found)//if object is found in the room
  {
    Serial.println(navArrayCount);
    Serial.print("Found Object-ID: ");
    Serial.print(objectID);
    Serial.print("in corridor ");
    Serial.print(navData[navArrayCount].corrID);
    Serial.print(" Room ");
    Serial.print(navData[navArrayCount].roomID);
    objectID++;
  }
  stop();//wait for user to take it out from room
}

void gotoRightRoom()
{
  navData[navArrayCount].leftRight = 'r';
  navArrayCount++;

  Serial.print("Going right into the room. No. ");
  Serial.println(roomID);
  //motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
  //delay(1500);
  motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED); //go in to scan room
  delay(1000);
  scanRoom();
  motors.setSpeeds(-REVERSE_SPEED, -REVERSE_SPEED); //when done scanning go back
  delay(1000);
  //motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED); //turn back to same direction as entered
  //delay(1500);
  motors.setSpeeds(0, 0);
  resumed = true; keepInCorridor();
}

void gotoLeftRoom()
{
  navData[navArrayCount].leftRight = 'l';
  navArrayCount++;

  Serial.print("Going left into the room. No. ");
  Serial.println(roomID);
  //motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
  //delay(1500);
  motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED); //go in to scan
  delay(1000);
  scanRoom();
  // motors.setSpeeds(-REVERSE_SPEED, -REVERSE_SPEED); //when done scanning go back
  //delay(1000);
  //motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED); //turn back to same direction as entered
  //delay(1500);
  motors.setSpeeds(0, 0);
  resumed = true;
  keepInCorridor();
}

void gotoRightCorridor()
{

  //turn right and continue //new corridor
  Serial.print("Going right into the corridor No. ");
  Serial.println(corridorID);
  motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
  delay(1500);
  resumed = true;
  keepInCorridor();
}

void gotoLeftCorridor()
{
  //turn left and continue  //new corridor
  Serial.print("Going left into the corridor. No ");
  Serial.println(corridorID);
  motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
  delay(1500);
  resumed = true;
  keepInCorridor();
}

void turnBack()//Task 5
{
  stop();
  if (navArrayCount > 0)
  {
    for (int i = 0; i < navArrayCount; i++)
    {
      //Serial.print("Room/Corridor ");
      //Serial.print(navData[i].roomCorr);
      Serial.print("Left/Right ");
      Serial.print(" : ");
      Serial.print(navData[i].leftRight);
      Serial.print(" in Corridor No. ");
      Serial.print(navData[i].corrID);
      Serial.print(" Room No.");
      Serial.println(navData[i].roomID);
    }
  }
}

void processCommand(char dir)
{
  /*  if(navArrayCount == sizeof(navData)-4)
    {
       nav temp[sizeof(navData)+10];
       for(int i=0; i< sizeof(navData);i++)
       {
        temp[i]=navData[i];
       }

       for(int i=0; i< sizeof(temp);i++)
       {
        navData[i]=temp[i];
       }

    }*/
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
    case 'k': {        //if its corridor
        roomID = 0;
        isRoom = false;
        navData[navArrayCount].roomCorr = 'k';
        corridorID++;
        navData[navArrayCount].corrID = corridorID;
      } break;
    case 'j': {        //if its room
        isRoom = true;
        navData[navArrayCount].roomCorr = 'j';
        roomID++;

        navData[navArrayCount].corrID = corridorID;
        navData[navArrayCount].roomID = roomID;
      } break;
    //go right room/corridor
    case 'r': {
        if (isRoom)
        {
          //gotoRightRoom();
          navData[navArrayCount].leftRight = 'l';
          navArrayCount++;
          Serial.print("Going right into the room. No. ");
          Serial.println(roomID);
        }
        else
        {
          gotoRightCorridor();
        }
      } break;
    //go left room/corridor
    case 'l': {
        if (isRoom)
        {
          //gotoLeftRoom();
          navData[navArrayCount].leftRight = 'r';
          navArrayCount++;
          Serial.print("Going left into the room. No. ");
          Serial.println(roomID);
        }
        else
        {
          gotoLeftCorridor();
        }
      } break;
    case 'x':
      {
        scanRoom();
      } break;
    //for turning back
    case 'e': {
        turnBack();
      } break;

    default: Serial.println("Invalid command");
  }
}


void loop()
{
  //Serial.print("Distance: ");
  Serial.print(sonar.ping_cm()); // Send ping, get distance in cm and print result (0 = outside set distance range)
  Serial.println("cm");

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

