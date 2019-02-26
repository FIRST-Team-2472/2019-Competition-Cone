/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.actions;


import frc.robot.Switchbox;

/**
 * Add your docs here.
 */
public class SwitchWaitAction implements Actionable{

    private final Switchbox switchbox;
    private boolean stablePosition;
    private int switchX, switchY;

    public SwitchWaitAction(Switchbox box, int switchX, int switchY) {
        switchbox = box;
        this.switchX = switchX;
        this.switchY = switchY;
    }

    public SwitchWaitAction(Switchbox box) {
        this(box, 0, 3);
    }

    @Override
    public void startAction() {
        stablePosition = switchbox.getSwitch(switchX, switchY);
    }

    @Override
    public void periodic() {

    }

    @Override
    public void endAction() {

    }

    @Override
    public boolean isFinished() {
        return switchbox.getSwitch(switchX, switchY) != stablePosition;
    }


}
