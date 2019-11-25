/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.Constants;


/**
 * Add your docs here.
 */
public class Drive {
    private TalonSRX leftFoot;
    private TalonSRX rightFoot;
    private TalonSRX middleleftFoot;
    private TalonSRX middlerightFoot;

    public Drive(int leftFootID, int rightFootID, int middleleftFootID, int middlerightFootID) {
        leftFoot = new TalonSRX(leftFootID);
        rightFoot = new TalonSRX(rightFootID);
        middleleftFoot = new TalonSRX(middleleftFootID);
        middlerightFoot = new TalonSRX(middlerightFootID);
        double ramp = .5;
        leftFoot.configOpenloopRamp(ramp);
        rightFoot.configOpenloopRamp(ramp);
        middleleftFoot.configOpenloopRamp(ramp);
        middlerightFoot.configOpenloopRamp(ramp);
        rightFoot.setInverted(true);
        middlerightFoot.setInverted(true);
    }

    public Drive() {
        this(Constants.FRONT_LEFT_MOTOR_ID, Constants.FRONT_RIGHT_MOTOR_ID, Constants.BACK_LEFT_MOTOR_ID, Constants.BACK_RIGHT_MOTOR_ID);
    }


    public void tankDrive(double leftSpeed, double rightSpeed) {
        runBackLeft(leftSpeed);
        runBackRight(rightSpeed);
        runMiddleLeft(leftSpeed);
        runMiddleRight(rightSpeed);
    }

    public void tankDrive(double[] leftRightArray) {
        if (leftRightArray.length != 2) {
            throw new IllegalArgumentException("Pass a 2 length array to set tankDrive speed");
        }
        tankDrive(leftRightArray[0], leftRightArray[1]);
    }

    public void runDriveMotors(double speed) {
        tankDrive(speed, speed);
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
    public void runMiddleLeft(double speed) {
        middleleftFoot.set(ControlMode.PercentOutput, speed);
    }

    public void runMiddleRight(double speed) {
        middlerightFoot.set(ControlMode.PercentOutput, speed);
    }

    //double test() {
        //return 0.5;
    //}
}