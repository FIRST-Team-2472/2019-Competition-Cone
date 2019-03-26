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
public class RaiseClimbingPistions extends TimerBase {

    //public enum Type {FRONT_EXTEND, REAR_EXTEND, FRONT_RETRACT, REAR_RETRACT};
    public enum Type {FRONT, REAR, ALL};


    private final Type type;


    public RaiseClimbingPistions(RaiseClimbingPistions.Type area, double time) {
        super(time);
        type = area;
    }

    public RaiseClimbingPistions(RaiseClimbingPistions.Type area) {
        this(area, 2);
    }

    @Override
    public void periodic() {
        switch (type) {
            case FRONT:
                Robot.climb.raiseFront();
                break;
            case REAR:
                Robot.climb.raiseRear();
                break;
            case ALL:
                Robot.climb.raiseRear();
                Robot.climb.raiseFront();
        }
    }

    @Override
    public void endAction() {
    }

}
