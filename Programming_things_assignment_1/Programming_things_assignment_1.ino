#include <ZumoBuzzer.h>
#include <ZumoMotors.h>
#include <Pushbutton.h>
#include <QTRSensors.h>
#include <ZumoReflectanceSensorArray.h>
#include <NewPing.h>

#define LED 13
#define TRIGGER_PIN       12  // Arduino pin tied to trigger pin on the ultrasonic sensor.
#define ECHO_PIN          11  // Arduino pin tied to echo pin on the ultrasonic sensor.
#define MAX_DISTANCE      200 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.
#define ABOVE_LINE(sensor)((sensor) > QTR_THRESHOLD) //For checking if the reflectance sensor is on the black line
#define QTR_THRESHOLD     1500 // microseconds
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
unsigned long startTime, endTime;
ZumoReflectanceSensorArray sensors(QTR_NO_EMITTER_PIN);

int navArrayCount;      //For keeping track of index for the nav data
int objectID;           //For keeping track of objects that are found in rooms
int corridorID;         //Counting different corridors
int roomID;             //Counting number of each room in each corridor
int roomIDCopy;         //For remembering the rooms in last corridor
unsigned long timeSpentInCorridor; //To be used when coming back
boolean inSubCorridor;
boolean isRoom;
boolean isExitingSubCorridor;
//To check if we are going to enter a room or corridor

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
  startTime = 0;
  endTime = 0;
  isExitingSubCorridor = false;
  Serial.begin(38400);  //Set baud rate
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

void keepInCorridor()//Keep within black lines
{
  //If left sensor as well right sensor are on the black line the it means its a wall
  //If there was a line on the left immidiatly after the line on the righ tor vice versa
  //then it means its a corner

  sensors.read(sensor_values);
  unsigned long elapsedTime = endTime - startTime;//if there was a a left wall immidiately afer right wall that its a corner
  if (elapsedTime > 20 && elapsedTime < 1000)//If there is a ping pong motion then its a corner
  {
    Serial.println("CORNER");
    motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
    delay(300);
    motors.setSpeeds(0, 0);
    motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
    delay(800);
    stop();
    if (isExitingSubCorridor)
    {
      turnLastCorridorDirection();
    }
  } else if (ABOVE_LINE(sensor_values[0]))
  {
    delay(50);
    sensors.read(sensor_values);
    if (ABOVE_LINE(sensor_values[5]))
    {
      Serial.println("FRONT WALL");
      stop();
      motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
      delay(300);
      stop();
      if (isExitingSubCorridor)
      {
        turnLastCorridorDirection();
      }
    } else
    {
      Serial.println("LEFT WALL");
      motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
      delay(REVERSE_DURATION);
      motors.setSpeeds(TURN_SPEED, -TURN_SPEED);
      delay(TURN_DURATION);
      startTime = millis();
    }

  } else if (ABOVE_LINE(sensor_values[5]))
  {
    delay(50);
    sensors.read(sensor_values);
    if (ABOVE_LINE(sensor_values[0]))
    {
      Serial.println("FRONT WALL");
      stop();
      motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
      delay(300);
      stop();
      if (isExitingSubCorridor)
      {
        turnLastCorridorDirection();
      }
    } else
    {
      Serial.println("RIGHT WALL");
      motors.setSpeeds(-FORWARD_SPEED, -FORWARD_SPEED);
      delay(REVERSE_DURATION);
      motors.setSpeeds(-TURN_SPEED, TURN_SPEED);
      delay(TURN_DURATION);
      motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);
      endTime = millis();
    }
  }
  else
  {
    // otherwise, go straight
    motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);
  }
}


void turnLastCorridorDirection()//Find exit of the sub-corridor by detecting a wall or corner in the front
{
  nav corrData;
  for (int i = navArrayCount; i >= 0; i--)
  {
    if (navData[i].corrID == corridorID + 1)
    {
      corrData = navData[i];
    }
  }
  if (corrData.leftRight == 'r')
  {
    motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
    delay(1500);
  }
  else if (corrData.leftRight == 'l')
  {
    motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
    delay(1500);
  }
  stop();
  isExitingSubCorridor = false;
  forward();
}

void exitSubCorridor()
{
  Serial.print("Exiting corridor");
  Serial.println(corridorID);
  inSubCorridor = false;

  if (navData[navArrayCount].leftRight == 'r') {
    motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
    delay(3000);
  } else
  {
    motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
    delay(3000);
  }
  forward();
  roomID=roomIDCopy;
}

void stop() {
  motors.setSpeeds(0, 0);
  resumed = false;
  Serial.println("Stopping");
}

void scanRoom() //For searching rooms
{
  boolean found = false;
  Serial.println("Scanning");
  motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED); //Go in the room
  delay(1000);
  for (int i = 0; i < 8; i++)//rotate and scan.
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
    Serial.print("Found Object. ID: ");
    Serial.println(objectID);
    Serial.print("in Corridor No.");
    Serial.print(corridorID);
    Serial.print(" Room ");
    Serial.println(roomID);
    objectID++;
  }
  stop();//wait for user to take it out from room
}


void gotoRightCorridor()
{
  //turn right and continue //new corridor
  Serial.print("Going right into the corridor No. ");
  Serial.println(corridorID);
  motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
  delay(1300);
  resumed = true;
  keepInCorridor();
}

void gotoLeftCorridor()
{
  //turn left and continue  //new corridor
  Serial.print("Going left into the corridor. No ");
  Serial.println(corridorID);
  motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
  delay(1300);
  resumed = true;
  keepInCorridor();
}

void turnBack()//Task 5
{
   for (int i = 0; i < navArrayCount; i++)
  {
    if (navData[i].leftRight == 'l')
      Serial.print("Left in ");
    else
      Serial.print(" Right in ");
    if (navData[i].roomCorr == 'k')
    {
      Serial.print("Corridor ");
      Serial.print("- Corridor No. ");
      Serial.println(navData[i].corrID);
    }
    else
    {
      Serial.print("Room ");
      Serial.print("- Corridor No. ");
      Serial.print(navData[i].corrID);
      Serial.print(" Room No.");
      Serial.println(navData[i].roomID);
    }
  }
  sortByCorridor();
  /*
  motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
  delay(2800);
  //becuase we are return left and right are flipped
  for (int i = navArrayCount; i >= 0; i--)
  {
    if (navData[i].leftRight == 'l' && navData[i].roomCorr == 'k')//right corridor
    { //turn right
      stop();
      motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
      delay(1500);
    } else if (navData[i].leftRight == 'r' && navData[i].roomCorr == 'k')//left corridor
    {
      stop();
      motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
      delay(1500);
    } else if (navData[i].leftRight == 'l' && navData[i].roomCorr == 'j')//right room
    { //turn right
      stop();
      motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);

      scanRoom();
      delay(1500);
    } else if (navData[i].leftRight == 'r' && navData[i].roomCorr == 'j')//left room
    {
      stop();
      motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
      scanRoom();
      delay(1500);
    }
    motors.setSpeeds(FORWARD_SPEED, FORWARD_SPEED);
    delay(5000);
  }*/
  stop();
}

void sortByCorridor()
{
  for (int i = 0; i < navArrayCount - 1; i++)
  {
    int min = i;
    for (int j = i + 1; j < navArrayCount; j++)
      if (navData[j].corrID < navData[min].corrID) min = j;
    nav temp = navData[i];
    navData[i].corrID = navData[min].corrID;
    navData[min] = temp;
  }
}

void forward()
{
  endTime = 0;
  startTime = 0;
  resumed = true;
  keepInCorridor();
}

void processCommand(char dir)
{

  switch (dir)
  {
    case 'w': {
        //Forward
        forward();
      } break;
    case 'c': {       //Continue
        //Reset left and right wall hits to allow control
        forward();
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
    case 'k': {        //if its a new corridor
        roomIDCopy=roomID;
        roomID = 0;
        isRoom = false;
        inSubCorridor = true;
        navData[navArrayCount].roomCorr = 'k';
        corridorID++;
        navData[navArrayCount].corrID = corridorID;
      } break;
    case 'j': {        //if its a room
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
          motors.setSpeeds(FORWARD_SPEED, -FORWARD_SPEED);
          delay(1500);
          stop();
          navData[navArrayCount].leftRight = 'r';
          navArrayCount++;
          Serial.print("Going right into the room. No. ");
          Serial.println(roomID);
        }
        else
        {
          navData[navArrayCount].leftRight = 'r';
          navArrayCount++;
          gotoRightCorridor();
        }
      } break;
    //go left room/corridor
    case 'l': {
        if (isRoom)
        {
          motors.setSpeeds(-FORWARD_SPEED, FORWARD_SPEED);
          delay(1500);
          stop();
          //gotoLeftRoom();
          navData[navArrayCount].leftRight = 'l';
          navArrayCount++;
          Serial.print("Going left into the room. No. ");
          Serial.println(roomID);
        }
        else
        {
          navData[navArrayCount].leftRight = 'r';
          navArrayCount++;
          gotoLeftCorridor();
        }
      } break;
    case 'x':
      {
        scanRoom();
      } break;
    case 'z'://exit corridor
      {
        if (inSubCorridor && corridorID > 0)
        {
          exitSubCorridor();
          isExitingSubCorridor = true;
        }
        corridorID--;
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

