package org.usfirst.frc.team2643.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class Elevator
{
	/**
	 * Initialize Elevator 
	 * @param lsm - linear slide motor
	 */
	/*public Elevator()
	{
		//defaultPIDLSMotor();
		//RobotMap.elevator1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 20);
	}*/
	
	/**
	 * Initialize Elevator 
	 * @param lsm - linear slide motor
	 * @param profile - PID profile
	 */
	/*public Elevator(int profile)
	{
		//defaultPIDLSMotor();
		//RobotMap.elevator1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, profile, 20);
	}*/
	
	/**
	 * Default PID profile (0)
	 */
	public static void defaultPIDLSMotor()
	{
		RobotMap.elevator1.selectProfileSlot(EnvironmentVariables.defaultPID, 0); 
		RobotMap.elevator1.config_kF(0, EnvironmentVariables.PIDF, 20); 
		RobotMap.elevator1.config_kP(0, EnvironmentVariables.PIDP, 20); 
		RobotMap.elevator1.config_kI(0, EnvironmentVariables.PIDI, 20); 
		RobotMap.elevator1.config_kD(0, EnvironmentVariables.PIDD, 20);
	}
	
	/**
	 * Configure PID profile
	 * @param profile - profile (0 or 1)
	 * @param fVal - f constant
	 * @param pVal - position constant
	 * @param iVal - integral constant
	 * @param dVal - derivative constant
	 */
	public static void configPIDProfile(int profile, double fVal, double pVal, double iVal, double dVal)
	{
		RobotMap.elevator1.selectProfileSlot(profile, 0); 
		RobotMap.elevator1.config_kF(0, fVal, 20); 
		RobotMap.elevator1.config_kP(0, pVal, 20); 
		RobotMap.elevator1.config_kI(0, iVal, 20); 
		RobotMap.elevator1.config_kD(0, dVal, 20);
	}
	
	/**
	 * Get encoder value for the elevator
	 * @return - encoder value for the elevator
	 */
	public static int getEncoderValues()
	{
		return RobotMap.elevator1.getSensorCollection().getQuadraturePosition() / 2;
	}
	
	/**
	 * Convert Feet to encoders and move
	 * @param pos - Movement by feet
	 */
	public static void moveElevatorToPosFeet(int feet)
	{
		int feetT = RobotMap.ticksPerFoot * feet;
		RobotMap.elevator1.set(ControlMode.Position, feet);
	}
	
	/**
	 * Convert inches to encoder and move
	 * @param pos - movement in inches
	 */
	public static void moveElevatorToPosInches(int inch)
	{
		int inchT = RobotMap.ticksPerInch * inch;
		RobotMap.elevator1.set(ControlMode.Position, inchT);
	}
	
	/**
	 * move elevator by movement rate
	 * @param value - double value (can be used with joystick values)
	 */
	public static void moveElevatorWithInput(Joystick stick)
	{
		RobotMap.elevator1.set(ControlMode.PercentOutput, stick.getRawAxis(1));
		
		/*if(getEncoderValues() > EnvironmentVariables.maxEncoderValue)
			if(value < 0)
				RobotMap.elevator1.set(ControlMode.PercentOutput, value);
		else
			RobotMap.elevator1.set(ControlMode.PercentOutput, value);
			
			 	System.out.println(value);*/
	}
	
	/**
	 * move elevator using POV on controller with a constant speed
	 * @param stick - joystick controller
	 */
	public static void moveElevatorUsingPOV(Joystick stick)
	{
		if(getEncoderValues() > EnvironmentVariables.maxEncoderValue)
			if(stick.getPOV() == 180)
				RobotMap.elevator1.set(ControlMode.PercentOutput, EnvironmentVariables.moveDownSpeed);
		else
			if(stick.getPOV() == 0)
				RobotMap.elevator1.set(ControlMode.PercentOutput, EnvironmentVariables.moveUpSpeed);
			else if(stick.getPOV() == 180)
				RobotMap.elevator1.set(ControlMode.PercentOutput, EnvironmentVariables.moveDownSpeed);
			else
				RobotMap.elevator1.set(0.0);
	}
}
