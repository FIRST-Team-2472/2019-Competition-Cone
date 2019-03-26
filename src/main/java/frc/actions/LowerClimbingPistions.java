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
public class LowerClimbingPistions extends TimerBase {

    public enum Type {FRONT, REAR, ALL};

    private Type chosen;

    public LowerClimbingPistions(LowerClimbingPistions.Type area, double time) {
        super(time);
        chosen = area;
    }

    public LowerClimbingPistions(LowerClimbingPistions.Type area) {
        this(area, 2);
    }

    @Override
    public void periodic() {
        switch (chosen) {
            case FRONT:
                Robot.climb.retractFront();
                break;
            case REAR:
                Robot.climb.retractRear();
                break;
            case ALL:
                Robot.climb.retractFront();
                Robot.climb.retractRear();
        }
    }

    @Override
    public void endAction() {
    }

}
