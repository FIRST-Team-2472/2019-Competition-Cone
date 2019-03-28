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
public class ClimbStep3 implements Actionable {


    @Override
    public void startAction() {

    }

    @Override
    public void periodic() {
        Robot.climb.creepForward();
    }

    @Override
    public void endAction() {
        Robot.climb.retractRear();
        Robot.climb.stopCreep();
    }

    @Override
    public boolean isFinished() {
        return Robot.distanceRear.getValue() > 2000;
    }

}
