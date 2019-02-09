/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


/**
 * Add your docs here.
 */
public class Drive {
    private TalonSRX frontLeft;
    private TalonSRX backLeft;
    private TalonSRX frontRight;
    private TalonSRX backRight;

    public Drive(int frontLeftID, int backLeftID, int frontRightID, int backRightID) {
        frontLeft = new TalonSRX(frontLeftID);
        backLeft = new TalonSRX(backLeftID);
        frontRight = new TalonSRX(frontRightID);
        backRight = new TalonSRX(frontRightID);
        frontRight.setInverted(true);
        backRight.setInverted(true);
    }


    public void tankDrive(double leftSpeed, double rightSpeed) {
        runBackLeft(leftSpeed);
        runFrontLeft(leftSpeed);
        runBackRight(rightSpeed);
        runFrontRight(rightSpeed);
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
        runFrontLeft(speed);
        runFrontRight(speed);
    }

    public void stopMotors() {
        runDriveMotors(0);
    }

    public void runFrontLeft(double speed) {
        frontLeft.set(ControlMode.PercentOutput, speed);
    }

    public void runBackLeft(double speed) {
        backLeft.set(ControlMode.PercentOutput, speed);
    }

    public void runFrontRight(double speed) {
        frontRight.set(ControlMode.PercentOutput, speed);
    }

    public void runBackRight(double speed) {
        backRight.set(ControlMode.PercentOutput, speed);
    }
}
