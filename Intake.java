package org.usfirst.frc.team2643.robot;

public class Intake {
	public void intake(double x, double y) {
		if(x>0.05) {
			RobotMap.leftIntake.set(x);
			RobotMap.rightIntake.set(x);
		}
		else if(y>0.05) {
			RobotMap.leftIntake.set(-y);
			RobotMap.rightIntake.set(-y);
		}
	}
}
