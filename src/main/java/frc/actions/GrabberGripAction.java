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
public class GrabberGripAction extends TimerBase {

    private final boolean grip;

    public GrabberGripAction(boolean constrict) {
        super(.1);
        grip = constrict;
    }

    @Override
    public void periodic() {
        if (grip) {
            Robot.hatchGrabber.grip();
        } else {
            Robot.hatchGrabber.release();
        }
    }

    @Override
    public void endAction() {
        Robot.hatchGrabber.relaxGrab();
    }
}
