/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.actions;

import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class ArmWheelsAction extends TimerBase {

    private double power;

    public ArmWheelsAction(double power, double time) {
        super(time);
        this.power = power;
    }

    @Override
    public void periodic() {
        Robot.armWheels.setMotorSpeed(power);
    }

    @Override
    public void endAction() {
        Robot.armWheels.stop();
    }
}
