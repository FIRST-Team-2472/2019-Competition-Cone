/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.sun.org.apache.bcel.internal.classfile.Constant;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class Climb {
    private TalonSRX leftCreep;
    private TalonSRX rightCreep;
    private DoubleSolenoid rearSolenoid;
    private DoubleSolenoid frontSolenoid;

    public Climb(int leftCreepID, int rightCreepID, int frontSolonoidForward, int frontSolonoidReverse,
            int rearSolenoidForward, int rearSolenoidReverse) {
        init(leftCreepID, rightCreepID, frontSolonoidForward, frontSolonoidReverse,
            rearSolenoidForward, rearSolenoidReverse);
    }

    public void init(int leftCreepID, int rightCreepID, int frontSolonoidForward, int frontSolonoidReverse,
            int rearSolenoidForward, int rearSolenoidReverse) {
        leftCreep = new TalonSRX(leftCreepID);
        rightCreep = new TalonSRX(rightCreepID);
        rearSolenoid = new DoubleSolenoid(frontSolonoidForward, frontSolonoidReverse);
        frontSolenoid = new DoubleSolenoid(rearSolenoidForward, rearSolenoidReverse);
    }

    public Climb() {
        init(Constants.CREEP_LEFT_MOTOR_ID, Constants.CREEP_RIGHT_MOTOR_ID, Constants.CLIMB_FRONT_FW_CHANNEL,
                Constants.CLIMB_FRONT_RV_CHANNEL, Constants.CLIMB_REAR_FW_CHANNEL, Constants.CLIMB_REAR_RV_CHANNEL);
    }

    public void raiseFront() {
        frontSolenoid.set(Value.kForward);
    }

    public void retractFront() {
        frontSolenoid.set(Value.kReverse);
    }

    public void raiseRear() {
        rearSolenoid.set(Value.kForward);
    }

    public void retractRear() {
        rearSolenoid.set(Value.kReverse);
    }

    public void creepForward() {
        leftCreep.set(ControlMode.PercentOutput, .2);
        rightCreep.set(ControlMode.PercentOutput, .2);
    }

    public void setLeft(double power) {
        leftCreep.set(ControlMode.PercentOutput, power);
    }
    
    public void setRight(double power) {
        leftCreep.set(ControlMode.PercentOutput, power);
    }
}
