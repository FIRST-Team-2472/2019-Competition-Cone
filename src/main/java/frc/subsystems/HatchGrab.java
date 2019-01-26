/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class HatchGrab {
    DoubleSolenoid doubleSolenoid1;
    DoubleSolenoid doubleSolenoid2;
    public HatchGrab() {
        HatchGraber(Constants.DOUBLE_SOLENIOD_FW_CHANNEL_1,Constants.DOUBLE_SOLENIOD_RV_CHANNEL_1,Constants.DOUBLE_SOLENIOD_FW_CHANNEL_2,Constants.DOUBLE_SOLENIOD_RV_CHANNEL_2);
    }

    public void HatchGraber(int FWC1, int RVC1 ,int FWC2,int RVC2) {
        doubleSolenoid1 = new DoubleSolenoid(FWC1, RVC1);
        doubleSolenoid2 = new DoubleSolenoid(FWC2, RVC2);
    }

    public void out() {
        doubleSolenoid1.set(DoubleSolenoid.Value.kReverse);
        doubleSolenoid2.set(DoubleSolenoid.Value.kForward);
    }

    public void in() {
        doubleSolenoid1.set(DoubleSolenoid.Value.kForward);
        doubleSolenoid2.set(DoubleSolenoid.Value.kReverse);
    }

    public void dS1Out() {
        doubleSolenoid1.set(DoubleSolenoid.Value.kReverse); 
    }

    public void dS2Out() {
        doubleSolenoid2.set(DoubleSolenoid.Value.kReverse); 
    }

    public void dS1In() {
        doubleSolenoid1.set(DoubleSolenoid.Value.kForward); 
    }

    public void dS2In() {
        doubleSolenoid2.set(DoubleSolenoid.Value.kForward); 
    }
}