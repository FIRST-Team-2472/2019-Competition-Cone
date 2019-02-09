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
public class ArmRaise {

    private DoubleSolenoid doubleSolenoidr;
    private DoubleSolenoid doubleSolenoidl;

    public ArmRaise() {
        doubleSolenoidr = new DoubleSolenoid(Constants.ARM_RAISER_FORWARD_CHANNEL_RIGHT, Constants.ARM_RAISER_REVERSE_CHANNEL_RIGHT);
        doubleSolenoidl = new DoubleSolenoid(Constants.ARM_RAISER_FORWARD_CHANNEL_LEFT, Constants.ARM_RAISER_REVERSE_CHANNEL_LEFT);
    }

    public void up() {
        doubleSolenoidr.set(DoubleSolenoid.Value.kForward);
        doubleSolenoidl.set(DoubleSolenoid.Value.kForward);
    }

    public void down() {
        doubleSolenoidr.set(DoubleSolenoid.Value.kReverse);
        doubleSolenoidl.set(DoubleSolenoid.Value.kReverse);
    }
}
