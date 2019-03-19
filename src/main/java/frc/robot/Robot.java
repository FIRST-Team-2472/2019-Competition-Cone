/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Drive;
import frc.actions.ActionQueue;
import frc.actions.ArmLiftAction;
import frc.actions.ArmWheelsAction;
import frc.actions.GrabberExtendAction;
import frc.actions.GrabberGripAction;
import frc.actions.JoinActions;
import frc.actions.WaitAction;
import frc.subsystems.ArmRaise;
import frc.subsystems.ArmWheels;
import frc.subsystems.Climb;
import frc.subsystems.HatchGrab;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Driver Inputs
  private Joystick leftStick;
  private Joystick rightStick;
  private XboxController driverController;
  private XboxController manipulatorController;
  private Switchbox box;

  private Compressor compressor;

  // A relay that would turn on and off a lightring
  // This would be for vision tracking
  private Relay lightring;

  // The subsystems for the robot
  public static Drive d;
  public static Climb climb;
  public static ArmRaise armRaise;
  public static ArmWheels armWheels;
  public static HatchGrab hatchGrabber;

  // Drive mode varibles
  public double LeftError;
  public double RightError;
  private boolean driveInverted;

  // Climbing actions and objects
  private ActionQueue testActions;
  public static AnalogInput distanceFront;
  public static AnalogInput distanceRear;
  
  // Vars for chaanging into clibin mode during teleop
  private long climbTimer;
  private boolean timerActive;
  private boolean weAreCLIMBING;
  private boolean climbInit;



  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Driver input
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    driverController = new XboxController(2);
    manipulatorController = new XboxController(4);
    box = new Switchbox(3);

    // Init compressor
    compressor = new Compressor(2);
    compressor.setClosedLoopControl(true);

    // Subsystems
    d = new Drive();
    armWheels = new ArmWheels();
    climb = new Climb();
    hatchGrabber = new HatchGrab();
    armRaise = new ArmRaise();

    lightring = new Relay(Constants.LIGHTRING_RELAY_ID);

    // Distace sensors for climbing
    distanceFront = new AnalogInput(1);
    distanceRear = new AnalogInput(0);

    // Init cameras
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
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
    // Because we are active turn on the lightring
    lightring.set(Value.kOn);
    // Because drivers control during autonomous call teleop Init
    teleopInit();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Because drivers control during autonomous call teleop periodic
    teleopPeriodic();
  }

  private double[] getJoystickControl(Joystick leftStick, Joystick rightStick) {
    // Start by getting the raw values
    double a = leftStick.getY();
    double b = rightStick.getY();

    // Subtract error gathered by zeroing;
    a -= LeftError;
    b -= RightError;

    // Deadzones. If the value is small ignore it.
    if(Math.abs(a) < 0.2) a = 0;
    if(Math.abs(b) < 0.2) b = 0;

    
    double power = 2;
    // This is quaring the values from the joysticks
    // thus making finer small adjusments
    a = a < 0 ? -Math.pow(a, power) : Math.pow(a, power);
    b = b < 0 ? -Math.pow(b, power) : Math.pow(b, power);

    return new double[]{a, b};
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
  boolean joysticksInited = false;
  @Override
  public void teleopInit() {
    // Because we started make sure the lightring is on
    lightring.set(Value.kOn);
    // This init sectino can be called twice so I put this in
    if (!joysticksInited) {
      LeftError = leftStick.getY();
      RightError = rightStick.getY();
      joysticksInited = true;
    }
    
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // the weAreCLIMBING section is a alternate mode where driving stops and the robot climb
    // XXX: teleop climbing has not been tested
    if (weAreCLIMBING) {
      if (!climbInit) {
        /* 
        actionQueue = new ActionQueue();
        actionQueue.addAction(new LowerPistions(2));
        actionQueue.addAction(new RaiseFrontPistions());
        actionQueue.addAction(new RaiseRearPistions());
        actionQueue.addAction(new DriveAction(.5));

        climbInit = true;
        */
        System.out.println("climbing mode actvated");
      }
      //actionQueue.step();
      return;
    }

    // Zero outs the joysticks
    if (leftStick.getRawButton(3)) {
      LeftError = leftStick.getY();
    }
    if (rightStick.getRawButton(3)) {
      RightError = rightStick.getY();
    }

    double[] leftRight;

    if (box.getSwitch(0, 1)) {
      leftRight = getJoystickControl(leftStick, rightStick);
    } else {
      leftRight = getXboxControl(driverController);
    }
    SmartDashboard.putNumber("left", leftRight[0]);
    SmartDashboard.putNumber("right", leftRight[1]);
    
    // This section of code is to revese the robot
    // When you flick the switch the "front" of the robot will be on the back
    // Picture:
    //    Pre Switch flip     Post Switch Flip
    // +-------------------+----------------------+
    // |                   |                      |
    // |   ^               |          (left) ^    |
    // |   |(left)         |                 |    |
    // |   |               |                 |    |
    // |  [+) --> (forward)|  (forward) <-- [+)   |
    // |   |               |                 |    |
    // |   | (right)       |      (right)    |    |
    // |   V               |                 V    |
    // +-------------------+----------------------+
    // [+) = robot

    // Top left switch 
    driveInverted = box.getSwitch(0, 0);
    // "advanced" logic to switch direction
    // XXX: review this logic because when driveInverted the drive is incorrect
    int LEFT = 0;
    int RIGHT = 1;
    d.tankDrive(driveInverted ? -leftRight[RIGHT] : leftRight[LEFT], driveInverted ? -leftRight[LEFT] : leftRight[RIGHT]);
    
    // Hatch grabber
    // A is push out hatch grabber
    // B is bring in hatch grabber
    if(manipulatorController.getAButton()) {
      hatchGrabber.out();
    } else if(manipulatorController.getBButton()) {
      hatchGrabber.in();
    } else {
      // Set the solonoid to relax and not favor in or out
      hatchGrabber.relaxPush();
    }

    // Hatch grabber again
    // X is to close the hatch grabber mechanism and grip a hatch
    // X is to open the hatch grabber mechanism and release a hatch (hopefuly not onto the floor)
    if(manipulatorController.getXButton()) {
      hatchGrabber.grip();
    } else if(manipulatorController.getYButton()) { 
      hatchGrabber.release();
    } else {
      // Set the solonoid to relax and not favor in or out
      hatchGrabber.relaxGrab();
    }

    // Arm raise lever thing
    // Left bumper to raise the arm
    // Right bumber to lower arm
    if(manipulatorController.getBumper(Hand.kLeft)) {
      armRaise.up();
    } else if (manipulatorController.getBumper(Hand.kRight)) {
      armRaise.down();
    } else {
      // Set the solonoid to relax and not favor in or out
      armRaise.relax();
    }
    armWheels.setMotorSpeed(manipulatorController.getY());

    /*
     * Box is like this to trigger climbing
     * 1 = forward 0 = back x = not used
     * 
     * +---------+
     * |         |
     * |         |
     * |         |
     * | x x x x |
     * | 1 0 1 0 |
     * +---------+
     *      
     * */
     
    // XXX: teleop climbing has not been tested
    if(box.getSwitch(1,0) && !box.getSwitch(1,1) && box.getSwitch(1,2) && !box.getSwitch(1,3)) {
      if (!timerActive) {
        timerActive = true;
        climbTimer = System.currentTimeMillis() + 1000;
      }
    } else {
      timerActive = false;
    }

    if (timerActive && System.currentTimeMillis() > climbTimer) {
      weAreCLIMBING = true;
    }
  }
  
  @Override
  public void testInit() {
    // This is initialzing a self test with many actions
    // NOTE: why do we have to join with a wait action
    testActions = new ActionQueue();
    testActions.addAction(new ArmWheelsAction(1, 2));
    testActions.addAction(new ArmWheelsAction(-1, 2));
    
    //testActions.addAction(new DriveAction(2, .2));
    //testActions.addAction(new DriveAction(2, -.2));

    testActions.addAction(new JoinActions(new GrabberExtendAction(true), new WaitAction(2)));
    testActions.addAction(new JoinActions(new GrabberExtendAction(false), new WaitAction(2)));
    
    testActions.addAction(new JoinActions(new GrabberGripAction(true), new WaitAction(2)));
    testActions.addAction(new JoinActions(new GrabberGripAction(false), new WaitAction(2)));

    testActions.addAction(new JoinActions(new ArmLiftAction(true), new WaitAction(2)));
    testActions.addAction(new JoinActions(new ArmLiftAction(false), new WaitAction(2)));

  }
  int loop;
  @Override
  public void testPeriodic() {
    //testActions.step();
    SmartDashboard.putNumber("Distance Front", (double)distanceFront.getValue());
    SmartDashboard.putNumber("Distance Rear", (double)distanceRear.getValue());
    if (loop++ > 100) {
      System.out.println("Distance Front "+distanceFront.getValue());
      System.out.println("Distance Rear "+distanceRear.getValue());
      loop = 0;
    }
    
    // Misc manual testing
    if(manipulatorController.getAButton())
    {
      hatchGrabber.out();
    } else if(manipulatorController.getBButton())
    {
      hatchGrabber.in();
    } else {
      hatchGrabber.relaxPush();
    }
    if(manipulatorController.getXButton())
    {
      armRaise.up();
    } else if(manipulatorController.getYButton())
    {
      armRaise.down();
    } else {
      armRaise.relax();
    }
    
    if (driverController.getAButton()) {
      hatchGrabber.grip();
    } else if (driverController.getBButton()) {
      hatchGrabber.release();
    } else {
      hatchGrabber.relaxGrab();
    }
    //d.runDriveMotors(manipulatorController.getY(Hand.kLeft));
    //armWheels.setMotorSpeed(manipulatorController.getY(Hand.kRight));
    if (manipulatorController.getAButton()) {
      climb.creepForward();
    } else {
      climb.stopCreep();

    }
    if(manipulatorController.getBumper(Hand.kLeft))
    {
      climb.raiseFront();
    } else if(manipulatorController.getTriggerAxis(Hand.kLeft) > 0.5) {
      climb.retractFront();
    } else {
      climb.frontOff();
    }

    if(manipulatorController.getBumper(Hand.kRight)) {
      climb.raiseRear();
    } else if(manipulatorController.getTriggerAxis(Hand.kRight) > 0.5) {
      climb.retractRear();
    } else {
      climb.rearOff();
    }
    
    /* Dangrous button mappings to test climbing
    if(manipulatorController.getBumper(Hand.kLeft)) {
      climb.raiseRear();
      climb.raiseFront();
    } else if (manipulatorController.getBumper(Hand.kRight)) {
      climb.retractFront();
      climb.retractRear();
    } else {
      climb.rearOff();
      climb.frontOff();
    }*/


  }

  @Override
  public void disabledInit() {
    lightring.set(Value.kOff);
  }
}
