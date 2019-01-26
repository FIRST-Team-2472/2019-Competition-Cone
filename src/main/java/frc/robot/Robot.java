/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.subsystems.ArmGrippers;
import frc.subsystems.Drive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Joystick leftStick;
  private Joystick rightStick;
  private XboxController driverController;
  private XboxController manipulatorController;
  private Switchbox box;
  private ArmGrippers armGrippers; 

  private Relay lightring;
  private boolean driveInverted;
  
  public static Drive d;


  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    leftStick = new Joystick(Constants.JOYSTICK_LEFT);
    rightStick = new Joystick(Constants.JOYSTICK_RIGHT);
    driverController = new XboxController(Constants.DRIVE_CONTROLLER);
    manipulatorController = new XboxController(Constants.MANIPULATOR_CONTROLLER);
    d = new Drive(Constants.FRONT_LEFT_MOTOR_ID, Constants.BACK_LEFT_MOTOR_ID, Constants.FRONT_RIGHT_MOTOR_ID, Constants.BACK_RIGHT_MOTOR_ID);
    box = new Switchbox(Constants.BOX_ID);
    armGrippers = new ArmGrippers(Constants.LEFT_GRIPPER_MOTOR_ID, Constants.RIGHT_GRIPPER_MOTOR_ID);
    lightring = new Relay(Constants.LIGHTRING_RELAY_ID);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    lightring.set(Value.kOn);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  private double[] getJoystickControl(Joystick leftStick, Joystick rightStick) {
    return new double[]{leftStick.getY(), rightStick.getY()};
  }

  private double[] getXboxControl(XboxController driverController) {
    double rightSide;
    double leftSide;
    double turn = driverController.getX(Hand.kLeft);
    boolean spin = driverController.getXButton();
    double speed = driverController.getTriggerAxis(Hand.kLeft);
    double slowdown = driverController.getTriggerAxis(Hand.kRight);
    boolean turningRight = turn > 0;

    if(spin){
				rightSide = turn;
				leftSide = -turn;
		} else {

			double baseSpeed = speed - slowdown;

			if(turningRight) {
        leftSide = baseSpeed * (1 - Math.abs(turn));
        rightSide = baseSpeed;
			} else {
				rightSide = baseSpeed * (1 - Math.abs(turn));
				leftSide=baseSpeed;
			}
		}
    return new double[]{leftSide, rightSide};
  }

  @Override
  public void teleopInit() {
    lightring.set(Value.kOn);
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    driveInverted = box.getSwitch(Constants.BOX_INVERSE_BUTTON_ROW, Constants.BOX_INVERSE_BUTTON_COLLUMN);
    double[] leftRight = getXboxControl(driverController);
    d.tankDrive(driveInverted ? -leftRight[1] : leftRight[0], driveInverted ? -leftRight[0] : leftRight[1]);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    lightring.set(Value.kOff);
  }
}
