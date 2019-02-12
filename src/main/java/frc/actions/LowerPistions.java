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
public class LowerPistions extends TimerBase {

    public LowerPistions(double time) {
        super(time);
    }

    @Override
    public void periodic() {
        //Robot.climb.raiseFront();
        //Robot.climb.raiseRear();
    }

    @Override
    public void endAction() {

    }
}
