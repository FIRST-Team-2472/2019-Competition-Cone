/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ArmWheels {
  TalonSRX r,l;
  public ArmWheels() {
    r = new TalonSRX(Constants.ARM_WHEELS_RIGHT);
    l = new TalonSRX(Constants.ARM_WHEELS_LEFT);
  }

  public void forward() {
    r.set(ControlMode.PercentOutput,1);
    l.set(ControlMode.PercentOutput,-1);
  }
  public void back() {
    r.set(ControlMode.PercentOutput,-1);
    l.set(ControlMode.PercentOutput,1);
  }
  public void stop() {
    r.set(ControlMode.PercentOutput,0);
    l.set(ControlMode.PercentOutput,0);
  }
}
