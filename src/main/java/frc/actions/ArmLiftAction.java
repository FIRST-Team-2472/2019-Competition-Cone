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
public class ArmLiftAction extends TimerBase {

    private final boolean up;

    public ArmLiftAction(boolean up) {
        super(1);
        this.up = up;
    }

    @Override
    public void periodic() {
        if (up) {
            Robot.armRaise.up();
        } else {
            Robot.armRaise.down();
        }
    }

    @Override
    public void endAction() {
        Robot.armRaise.relax();
    }

}
