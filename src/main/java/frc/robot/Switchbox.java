/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;

/**
 * Add your docs here.
 */
public class Switchbox extends GenericHID {

    private int switchesHeight;
    private int switchesWidth;
    
    public Switchbox(int port, int switchesHeight, int switchesWidth) {
        super(port);
        this.switchesHeight = switchesHeight;
        this.switchesWidth = switchesWidth;
    }

    public Switchbox(int port) {
        super(port);
        this.switchesHeight = Constants.BOX_SWITCHES_HEIGHT;
        this.switchesWidth = Constants.BOX_SWITCHES_WIDTH;
    }

    @Override
    public double getX(Hand hand) {
        return getRawAxis(0);
    }

    @Override
    public double getY(Hand hand) {
        return getRawAxis(1);
    }


    public boolean getSwitch(int row, int collumn) {
        if (row < 0 || row >= switchesHeight) {
            throw new IndexOutOfBoundsException("Row is not on this switchbox");
        }
        if (collumn < 0 || collumn >= switchesWidth) {
            throw new IndexOutOfBoundsException("Collumn is not on this switchbox");
        }
        int switchStart = 5;
        int rowOffset = 4 * row;
        int collumOffset = collumn;

        return getRawButton(switchStart + rowOffset + collumOffset);
    }

    public int getDialValue() {
        byte dial = 0b0000;
        if (getRawButton(1)) {
            dial |= 0b0001;
        }
        if (getRawButton(2)) {
            dial |= 0b0010;
        }
        if (getRawButton(3)) {
            dial |= 0b0100;
        }
        if (getRawButton(4)) {
            dial |= 0b1000;
        }
        return dial;
    }
}
