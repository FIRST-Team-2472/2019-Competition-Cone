/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PWM;
import frc.robot.Constants;


/**
 * Add your docs here.
 */
public class Drive {
    private TalonSRX leftFoot;
    private TalonSRX rightFoot;

    public Drive(int leftFootID, int rightFootID) {
        leftFoot = new TalonSRX(leftFootID);
        rightFoot = new TalonSRX(rightFootID);
        rightFoot.setInverted(true);
    }

    public Drive() {
        this(Constants.LEFT_MOTOR_ID, Constants.RIGHT_MOTOR_ID);
    }


    public void tankDrive(double leftSpeed, double rightSpeed) {
        runBackLeft(leftSpeed);
        runBackRight(rightSpeed);
    }

    public void tankDrive(double[] leftRightArray) {
        if (leftRightArray.length != 2) {
            throw new IllegalArgumentException("Pass a 2 length array to set tankDrive speed");
        }
        tankDrive(leftRightArray[0], leftRightArray[1]);
    }

    public void runDriveMotors(double speed) {
        runBackLeft(speed);
        runBackRight(speed);
    }

    public void stopMotors() {
        runDriveMotors(0);
    }

    public void runBackLeft(double speed) {
        leftFoot.set(ControlMode.PercentOutput, speed);
    }

    public void runBackRight(double speed) {
        rightFoot.set(ControlMode.PercentOutput, speed);
    }
}
