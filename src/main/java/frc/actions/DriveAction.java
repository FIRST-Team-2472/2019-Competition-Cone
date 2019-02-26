package frc.actions;

import frc.robot.Robot;


public class DriveAction extends TimerBase{
	private double speed = 0.5;
	private double lifetime;
	
	

	public DriveAction(double lifetime) {
		super(lifetime);
		this.lifetime = lifetime;
		
	}
	
	public DriveAction(double lifetime, double speed) {
		super(lifetime);
		this.lifetime = lifetime;
		this.speed = speed;
	}
	
	
	@Override
	public void periodic() {
		Robot.d.runDriveMotors(speed);
	}

	@Override
	public void endAction() {
		Robot.d.runDriveMotors(0);
		
	}

	public String toString() {
		return "DriveAction life:"+lifetime+" speed:"+speed;
		
	}

	

}
