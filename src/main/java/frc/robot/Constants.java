/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {
    // Motors
    public static final int LEFT_MOTOR_ID = 13;
    public static final int RIGHT_MOTOR_ID = 3;

    // Controller usb id
    public static final int DRIVE_CONTROLLER = 0;
    public static final int MANIPULATOR_CONTROLLER = 1;
    public static final int JOYSTICK_LEFT = 2;
    public static final int JOYSTICK_RIGHT = 3;
    public static final int BOX_ID = 1;

    // Switchbox settings
    public static final int BOX_SWITCHES_HEIGHT = 2;
    public static final int BOX_SWITCHES_WIDTH = 4;
    public static final int BOX_INVERSE_BUTTON = 5;
    public static final int BOX_INVERSE_BUTTON_ROW = 0;
    public static final int BOX_INVERSE_BUTTON_COLLUMN = 0;

    // Arm
    public static final int ARM_WHEELS_RIGHT = 1;
    public static final int ARM_WHEELS_LEFT = 5;
    public static final int ARM_RAISER_FORWARD_CHANNEL = 2;
    public static final int ARM_RAISER_REVERSE_CHANNEL = 3;
    public static final int ARM_RAISER_PCM = 1;

    public static final int LIGHTRING_RELAY_ID = 0;


    // Creep motors 
    public static final int CREEP_LEFT_MOTOR_ID = 12;
    public static final int CREEP_RIGHT_MOTOR_ID = 14;

    // Climbing pistions conts
    public static final int CLIMB_REAR_FW_CHANNEL = 2;
    public static final int CLIMB_REAR_RV_CHANNEL = 3;
    public static final int CLIMB_FRONT_FW_CHANNEL = 4;
    public static final int CLIMB_FRONT_RV_CHANNEL = 5;

    // Hatch Grabber conts
    public static final int HATCH_GRABBER_GRAB_FW_CHANNEL = 4;
    public static final int HATCH_GRABBER_GRAB_RV_CHANNEL = 5;
    public static final int HATCH_GRABBER_EXTEND_FW_CHANNEL = 0;
    public static final int HATCH_GRABBER_EXTEND_RV_CHANNEL = 1;

    
    // Buttons 
    public static final int HATCH_GRAB_BUTTON = 5;
    public static final int HATCH_RELEASE_BUTTON = 4;
    public static final int ARM_RAISE_BUTTON = 2;
    public static final int ARM_LOWER_BUTTON = 3;
    public static final int ARM_GRAB_BUTTON = 4;
    public static final int ARM_RELEASE_BUTTON = 5;

    public static final int DISTANCE_SENSOR_REAR_PORT = 0;
    public static final int DISTANCE_SENSOR_FRONT_PORT = 1;
}
