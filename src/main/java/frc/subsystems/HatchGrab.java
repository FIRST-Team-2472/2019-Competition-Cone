/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class HatchGrab {
    DoubleSolenoid grab;
    DoubleSolenoid push;
    public HatchGrab() {
        grab = new DoubleSolenoid(1, 4, 5);
        push = new DoubleSolenoid(1, 0, 1);
    }


    public void out() {
        push.set(DoubleSolenoid.Value.kReverse);
    }
    public void in() {
        push.set(DoubleSolenoid.Value.kForward);
    }
    public void relaxPush() {
        push.set(Value.kOff);
    }

    public void grip() {
        grab.set(DoubleSolenoid.Value.kForward);
    }
    public void release() {
        grab.set(DoubleSolenoid.Value.kReverse);
    }
    public void relaxGrab() {
        grab.set(Value.kOff);
    }

}