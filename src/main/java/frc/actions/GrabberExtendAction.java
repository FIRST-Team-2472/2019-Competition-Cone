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
public class GrabberExtendAction extends TimerBase {

    private final boolean extend;

    public GrabberExtendAction(boolean extend) {
        super(.1);
        this.extend = extend;
    }

    @Override
    public void periodic() {
        if (extend) {
            Robot.hatchGrabber.out();
        } else {
            Robot.hatchGrabber.in();
        }
    }

    @Override
    public void endAction() {
        Robot.hatchGrabber.relaxPush();
    }
}
