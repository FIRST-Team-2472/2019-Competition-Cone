/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ArmWheels {
  TalonSRX r,l;
  public ArmWheels(int left, int right) {
    r = new TalonSRX(left);
    l = new TalonSRX(right);
    l.setInverted(true);
  }

  public ArmWheels() {
    this(Constants.ARM_WHEELS_RIGHT, Constants.ARM_WHEELS_LEFT);
  }

  public void forward() {
    r.set(ControlMode.PercentOutput,1);
    l.set(ControlMode.PercentOutput,-1);
  }
  public void back() {
    r.set(ControlMode.PercentOutput,1);
    l.set(ControlMode.PercentOutput,-1);
  }
  public void setMotorSpeed(double speed)
  {
    r.set(ControlMode.PercentOutput,speed);
    l.set(ControlMode.PercentOutput,-speed);
  }

  public void stop() {
    r.set(ControlMode.PercentOutput,0);
    l.set(ControlMode.PercentOutput,0);
  }
}
