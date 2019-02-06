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
public class SpinAction extends TimerBase {

    public static enum Direction {LEFT, RIGHT}

    private final Direction direction;

    public SpinAction(double time, Direction direction) {
        super(time);
        this.direction = direction;
    }

    @Override
    public void periodic() {
        switch (direction) {
            case LEFT:
                Robot.d.runBackRight(.2);
                Robot.d.runFrontRight(.2);
                Robot.d.runBackLeft(-.2);
                Robot.d.runFrontLeft(-.2);
                break;
            case RIGHT:
                Robot.d.runBackRight(-.2);
                Robot.d.runFrontRight(-.2);
                Robot.d.runBackLeft(.2);
                Robot.d.runFrontLeft(.2);
                break;
        }
    }

    @Override
    public void endAction() {
        Robot.d.stopMotors();








        \












        

    }
}
