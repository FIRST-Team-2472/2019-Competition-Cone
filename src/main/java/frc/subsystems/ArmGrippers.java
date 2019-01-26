/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class ArmGrippers {
    private TalonSRX left;
    private TalonSRX right;

    public ArmGrippers(int leftID, int rightID) {
        left = new TalonSRX(leftID);
        right = new TalonSRX(rightID);

    }

    public void set(double power) {
        left.set(ControlMode.PercentOutput, power);
        right.set(ControlMode.PercentOutput, power);
    }

    public void out() {
        set(0.2);
    }

    public void in() {
        set(-0.2);
    }

    public void stop() {
        set(0);
    }
}
