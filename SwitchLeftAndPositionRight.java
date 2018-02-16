package org.usfirst.frc.team2643.robot;

public class SwitchLeftAndPositionRight {

	public static int autoProgramState = 0;

	public static void runPeriodic(){
		switch(autoProgramState){

		//this will release the arms
		case 0:
		{
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 0: Robot will release arms");
			}

			if(!AutoState.armsReleasing)
			{
				Robot.drive.setUpReleaseArms();
				AutoState.armsReleasing = true;
			}
			else if(Robot.drive.executeReleaseArms())
			{
				Robot.drive.finishReleaseArms();
				AutoState.armsReleasing = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will go forward until the it goes a little bit past the switch
		case 1:
		{
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 1: Move forward until past the switch");
			}
			int encoderGoal = EnvironmentVariables.ticksToPassSwitch;
			if(!AutoState.moving)
			{
				Robot.drive.setUpMove(encoderGoal);
				AutoState.moving = true;
			}
			else if(Robot.drive.executeMove())
			{
				Robot.drive.finishMove();
				AutoState.moving = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will turn left 90 degrees
		case 2:
		{
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 2: Turn left 90 degrees");
			}

			if(!AutoState.turning){
				Robot.drive.setUpTurn(-EnvironmentVariables.ticksTo90);
				AutoState.turning = true;
			}
			else if(Robot.drive.executeTurn())
			{
				Robot.drive.finishTurn();
				AutoState.turning = false;
				autoProgramState++;
			}
			break;
		}

		//the robot will move along the back of the wall until it passes the left end of the switch
		case 3:
		{
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 3: Move along back wall and pass the left end of the switch");
			}
			
			int encoderGoal = EnvironmentVariables.ticksWidthOfSwitch;
			if(!AutoState.moving)
			{
				Robot.drive.setUpMove(encoderGoal);
				AutoState.moving = true;
			}
			else if(Robot.drive.executeMove())
			{
				Robot.drive.finishMove();
				AutoState.moving = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will turn 90 degrees right
		case 4:
		{	
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 4: Turn 90 degrees right");
			}

			if(!AutoState.turning){
				Robot.drive.setUpTurn(-EnvironmentVariables.ticksTo90);
				AutoState.turning = true;
			}
			else if(Robot.drive.executeTurn()){
				Robot.drive.finishTurn();
				AutoState.turning = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will move forward so that it is next to the switch
		case 5:
		{	
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 5: Move forward until in the middle of the length of the switch");
			}
			int encoderGoal = EnvironmentVariables.ticksLengthOfSwitch/2;
			if(!AutoState.moving)
			{
				Robot.drive.setUpMove(encoderGoal);
				AutoState.moving = true;
			}
			else if(Robot.drive.executeMove())
			{
				Robot.drive.finishMove();
				AutoState.moving = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will turn 90 degrees left to face the switch plate
		case 6:
		{
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 6: Turn 90 degrees left to face the switch plate");
			}
			if(!AutoState.turning){
				Robot.drive.setUpTurn(-EnvironmentVariables.ticksTo90);
				AutoState.turning = true;
			}
			else if(Robot.drive.executeTurn()){
				Robot.drive.finishTurn();
				AutoState.turning = false;
				autoProgramState++;
			}
			break;
		}
		//the robot will drop the cube
		case 7:
		{	
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 7: Drop the cube onto the switch");
			}
			//DROP DAT CUBE 
			//...no
			//[GUNSHOTS]
			// ooOOoof!!!!
			//HE DED
			// r. i. p.
			break;
		}
		//the program is done
		case 8:
		{	
			if(RobotMap.DEBUG){
				System.out.println("SwitchLeftAndPositionRight Case 8: Program is done");
			}
			break;
		}
		}
	}
}
