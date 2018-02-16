package org.usfirst.frc.team2643.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//The V is to prevent it from conflicting with a class name
	final String crossAutoLineOnlyOption = "CrossAutoLineOnly";
	final String positionLeftOption = "PositionLeft";
	final String positionMiddleOption = "PositionMiddle";
	final String positionRightOption = "PositionRight";

	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	int driveState = 0;


	//double batteryVoltage = DriverStation.getInstance().getBatteryVoltage();

	public static Drive drive;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault(crossAutoLineOnlyOption, crossAutoLineOnlyOption);

		chooser.addObject(positionLeftOption, positionLeftOption);
		chooser.addObject(positionMiddleOption, positionMiddleOption);
		chooser.addObject(positionRightOption, positionRightOption);

		SmartDashboard.putData("Auto choices", chooser);

		drive = new Drive(
				RobotMap.leftDrive1,
				RobotMap.leftDrive2,
				RobotMap.rightDrive1,
				RobotMap.rightDrive2);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		drive.setToPositionMode();
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		RobotMap.elevator1.getSensorCollection().setQuadraturePosition(0, 10);

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L' && !autoSelected.equals(crossAutoLineOnlyOption))
		{
			autoSelected = "SwitchLeftAnd"  + autoSelected;
		} 
		else if(gameData.charAt(0) == 'R' && !autoSelected.equals(crossAutoLineOnlyOption))
		{
			autoSelected = "SwitchRightAnd" + autoSelected;
		}

		System.out.println("Auto selected: " + autoSelected);
		AutoState.armsReleasing = false;	
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch(autoSelected) {
		case "CrossAutoLineOnly": 
			CrossAutoLineOnly.runPeriodic();
			break;
		case "SwitchLeftAndPositionLeft":
			SwitchLeftAndPositionLeft.runPeriodic();
			break;
		case "SwitchLeftAndPositionMiddle":
			SwitchLeftAndPositionMiddle.runPeriodic();
			break;
		case "SwitchLeftAndPositionRight":
			SwitchLeftAndPositionRight.runPeriodic();
			break;
		case "SwitchRightAndPositionLeft":
			SwitchRightAndPositionLeft.runPeriodic();
			break;
		case "SwitchRightAndPositionMiddle": 
			SwitchRightAndPositionMiddle.runPeriodic();
			break;
		case "SwitchRightAndPositionRight": 
			SwitchRightAndPositionRight.runPeriodic();
			break;
		}
	}


	@Override
	public void teleopInit() {
		drive.setToPercentValue();
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		/** Drive code*/
		//TODO TEST!!!
		
		if(driveState == 0) { //0 is Tank Drive
			SRXtankDrive(RobotMap.driveStick.getRawAxis(1), RobotMap.driveStick.getRawAxis(5));
		}
		else if(driveState==1) {
			SRXarcadeDrive(RobotMap.driveStick.getRawAxis(0), RobotMap.driveStick.getRawAxis(1));
		}
		
		if(RobotMap.driveStick.getRawButton(7)) {
			driveState=0;
		}
		else if(RobotMap.driveStick.getRawButton(8)) {
			driveState=1;
		}

		/* Elevator code
		 * -winding it up moves the elevator up!!!!!
		 * -unwinding it will drop the elevator
		 * -check brake mode on talons
		 * -encoders
		 * -limit switch on the bottom
		 */
		
		System.out.println(Elevator.getEncoderValues());
		
		Elevator.moveElevatorWithInput(RobotMap.opStick);
		//RobotMap.elevator1.set(RobotMap.opStick.getRawAxis(1));
		
		//RobotMap.elevator1.set(ControlMode.PercentOutput, RobotMap.opStick.getRawAxis(1));
		
		//TODO TEST
		/*if(RobotMap.opStick.getPOV() == 0){
			if(RobotMap.elevator1.getSensorCollection().getQuadraturePosition() == RobotMap.slideBeforeTopLimit){
				RobotMap.elevator1.set(RobotMap.slideHoverSpeed);
			}else{
				RobotMap.elevator1.set(RobotMap.slideRaisingSpeed);
			}
		}
		
		else if(RobotMap.opStick.getPOV() == 180){
			if (RobotMap.elevator1.getSensorCollection().getQuadraturePosition() == 0) {
				RobotMap.elevator1.set(0);
			}else {
				RobotMap.elevator1.set(RobotMap.slideLoweringSpeed);
			}
		}
		else if(RobotMap.elevator1.getSensorCollection().getQuadraturePosition() != 0){
			RobotMap.elevator1.set(RobotMap.slideHoverSpeed);
		}
		else {
			RobotMap.elevator1.set(0);
		}*/
	}

		//Ramp Code
		//TODO TEST!!! TEST!!! TEST!!!
		/*if(RobotMap.opStick.getRawButton(RobotMap.RightRampUp)){
			RobotMap.RightFrontSolenoid.set(true);
			RobotMap.RightBackSolenoid.set(true);
		}
		else if(RobotMap.opStick.getRawButton(RobotMap.RightRampDown)){
			RobotMap.RightFrontSolenoid.set(false);
			RobotMap.RightBackSolenoid.set(false);
		}

		if(RobotMap.opStick.getRawButton(RobotMap.LeftRampUp)){
			RobotMap.LeftFrontSolenoid.set(true);
			RobotMap.LeftBackSolenoid.set(true);
		}
		else if(RobotMap.opStick.getRawButton(RobotMap.LeftRampDown)){
			RobotMap.LeftFrontSolenoid.set(false);
			RobotMap.LeftBackSolenoid.set(false);
		}

	}
*/ //Solenoid Comment
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		System.out.println("DO NOT RUN AT FULL SPEED IF YOU DON'T WANT TO BREAK THE ROBOT");
		if(RobotMap.driveStick.getRawButton(0) == true){
			RobotMap.leftDrive1.set(RobotMap.driveStick.getRawAxis(5));
			System.out.println(drive.getLeftEncoder());
		}

		else if(RobotMap.driveStick.getRawButton(1) == true){
			RobotMap.leftDrive2.set(RobotMap.driveStick.getRawAxis(5));
			System.out.println(drive.getLeftEncoder());
		}


		else if(RobotMap.driveStick.getRawButton(3) == true){
			RobotMap.rightDrive1.set(RobotMap.driveStick.getRawAxis(5));
			System.out.println(drive.getRightEncoder());
		}

		else if(RobotMap.driveStick.getRawButton(4) == true){
			RobotMap.rightDrive2.set(RobotMap.driveStick.getRawAxis(5));
			System.out.println(drive.getRightEncoder());
		}


		else if(RobotMap.driveStick.getRawButton(6) == true){
			RobotMap.elevator1.set(RobotMap.slideRaisingSpeed);
			if(RobotMap.elevator1.getSensorCollection().getQuadraturePosition() == RobotMap.slideBeforeTopLimit){
				RobotMap.elevator1.set(RobotMap.slideHoverSpeed);
			}
			System.out.println(RobotMap.elevator1.getSensorCollection().getQuadraturePosition());
		}

		else if(RobotMap.driveStick.getRawButton(7) == true){
			RobotMap.elevator1.set(RobotMap.slideLoweringSpeed);
			if(RobotMap.elevator1.getSensorCollection().getQuadraturePosition() == 0){
				RobotMap.elevator1.set(0);
			}
			System.out.println(RobotMap.elevator1.getSensorCollection().getQuadraturePosition());
		}

		else{
			drive.resetAllEncoder();
			RobotMap.elevator1.getSensorCollection().setQuadraturePosition(0, 10);
			drive.stopAllSpeed();
		}
	}
	
	public void SRXarcadeDrive(double x, double y) {
		if(x<-0.03 || x>0.03) { //If the given axis is pushed to the left or right, then set them to the value of that axis. 0.05 is the given dead zone and can be increased or decreased. Currently the deadzone is 5%
			setRightMotors(x);
			setLeftMotors(x);
		}
		else if(y>0.03||y<0.03) { //If the given axis is pushed up or
			setRightMotors(y);
			setLeftMotors(-y);
		}
		else { //If no joystick activity, set all motors to 0.
			setAll(0);
		}
	}
	
	public void SRXtankDrive(double x, double y) { //Very basic tank drive.
		setLeftMotors(-x);
		setRightMotors(y);
	}
	public static void setLeftMotors(double x) { //Set all of the motors on the left side to the given value.
		RobotMap.leftDrive1.set(x);
		RobotMap.leftDrive2.set(x);
	}
	public static void setRightMotors(double x) { //Set all of the motors on the right side to the given value.
		RobotMap.rightDrive1.set(x);
		RobotMap.rightDrive2.set(x);
	}
	public static void setAll(double x) { //Set all of the motors to the given value. 
		RobotMap.leftDrive1.set(x);
		RobotMap.leftDrive2.set(x);
		RobotMap.rightDrive1.set(x);
		RobotMap.rightDrive2.set(x);
	}
	
}