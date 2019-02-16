/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.subsystems.Drive;
import frc.actions.ActionQueue;
import frc.actions.ArmWheelsAction;
import frc.actions.DriveAction;
import frc.actions.GrabberExtendAction;
import frc.actions.GrabberGripAction;
import frc.actions.JoinActions;
import frc.actions.LowerPistions;
import frc.actions.RaiseFrontPistions;
import frc.actions.RaiseRearPistions;
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
  private Joystick leftStick;
  private Joystick rightStick;
  private XboxController driverController;
  private XboxController manipulatorController;
  private Switchbox box;
  private Compressor compressor;
  private Relay lightring;
  private boolean driveInverted;
  
  public static Drive d;
  public static Climb climb;
  public static AnalogInput distanceFront;
  public static AnalogInput distanceRear;
  public double LeftError;
  public double RightError;
  public static ArmRaise armRaise;
  public static ArmWheels armWheels;
  public static HatchGrab hatchGrabber;

  private long climbTimer;
  private boolean timerActive;
  private boolean weAreCLIMBING;
  private boolean climbInit;
  private ActionQueue actionQueue;

  private ActionQueue testActions;
  

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    leftStick = new Joystick(0);
    rightStick = new Joystick(1);
    LeftError = leftStick.getY();
    RightError = rightStick.getY();
    driverController = new XboxController(Constants.DRIVE_CONTROLLER);
    manipulatorController = new XboxController(2);
    d = new Drive();
    box = new Switchbox(3);
    armWheels = new ArmWheels();
    lightring = new Relay(Constants.LIGHTRING_RELAY_ID);
    climb = new Climb();
    hatchGrabber = new HatchGrab();
    armRaise = new ArmRaise();
    //distanceFront = new AnalogInput(Constants.DISTANCE_SENSOR_FRONT_PORT);
    //distanceRear = new AnalogInput(Constants.DISTANCE_SENSOR_REAR_PORT);
    compressor = new Compressor(2);
    compressor.setClosedLoopControl(true);
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
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
    //lightring.set(Value.kOn);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  private double[] getJoystickControl(Joystick leftStick, Joystick rightStick) {
    double a = leftStick.getY()-LeftError,b = rightStick.getY()-RightError;
    if(Math.abs(a) < 0.2) a = 0;
    if(Math.abs(b) < 0.2) b = 0;
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

  @Override
  public void teleopInit() {
    lightring.set(Value.kOn);
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
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

    driveInverted = box.getSwitch(0, 0);
    double[] leftRight;
    if (box.getSwitch(0, 1)) {
      leftRight = getJoystickControl(leftStick, rightStick);
    } else {
      leftRight = getXboxControl(driverController);
    }
    SmartDashboard.putNumber("left", leftRight[0]);
    SmartDashboard.putNumber("right", leftRight[1]);
    int LEFT = 0;
    int RIGHT = 1;
    d.tankDrive(driveInverted ? -leftRight[RIGHT] : leftRight[LEFT], driveInverted ? -leftRight[LEFT] : leftRight[RIGHT]);
    
    //d.tankDrive(leftRight);
    if(manipulatorController.getAButton()){ //A
      hatchGrabber.out();
    } else if(manipulatorController.getBButton()){ //B
      hatchGrabber.in();
    } else {
      hatchGrabber.pushOff();
    }
    if(manipulatorController.getXButton()){ //X
      hatchGrabber.grip();
    } else if(manipulatorController.getYButton()){ //Y
      hatchGrabber.release();
    } else {
      hatchGrabber.grabOff();
    }
    if(manipulatorController.getBumper(Hand.kLeft)) {
      armRaise.up();
    } else if (manipulatorController.getBumper(Hand.kRight)) {
      armRaise.down();
    } else {
      armRaise.off();
    }
    armWheels.setMotorSpeed(manipulatorController.getY());

    /*
     * Box is like this
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
    testActions = new ActionQueue();
    testActions.addAction(new ArmWheelsAction(1, 2));
    testActions.addAction(new ArmWheelsAction(-1, 2));
    
    testActions.addAction(new DriveAction(2, .2));
    testActions.addAction(new DriveAction(2, -.2));

    testActions.addAction(new JoinActions(new GrabberExtendAction(true), new WaitAction(2)));
    testActions.addAction(new JoinActions(new GrabberExtendAction(false), new WaitAction(2)));
    
    testActions.addAction(new JoinActions(new GrabberGripAction(true), new WaitAction(2)));
    testActions.addAction(new JoinActions(new GrabberGripAction(false), new WaitAction(2)));

  }

  @Override
  public void testPeriodic() {
    //testActions.step();

    if(manipulatorController.getAButton())
    {
      hatchGrabber.out();
    }
    if(manipulatorController.getXButton())
    {
      armRaise.up();
    }
    if(manipulatorController.getYButton())
    {
      armRaise.down();
    }
    if(manipulatorController.getBButton())
    {
      hatchGrabber.in();
    }
    if (driverController.getAButton()) {
      hatchGrabber.grip();
    } else if (driverController.getBButton()) {
      hatchGrabber.release();
    } else {
      hatchGrabber.grabOff();
    }
    //d.runDriveMotors(manipulatorController.getY(Hand.kLeft));
    //armWheels.setMotorSpeed(manipulatorController.getY(Hand.kRight));
    climb.setCreep(manipulatorController.getX(Hand.kLeft));
    if(manipulatorController.getBumper(Hand.kLeft))
    {
      //climb.raiseFront();
    } else if(manipulatorController.getTriggerAxis(Hand.kLeft) > 0.5) {
      //climb.retractFront();
    } else {
      //climb.frontOff();
    }

    if(manipulatorController.getBumper(Hand.kRight)) {
      //climb.raiseRear();
    } else if(manipulatorController.getTriggerAxis(Hand.kRight) > 0.5) {
      //climb.retractRear();
    } else {
      //climb.rearOff();
    }
    if(manipulatorController.getBumper(Hand.kLeft)) {
      climb.raiseRear();
      climb.raiseFront();
    } else if (manipulatorController.getBumper(Hand.kRight)) {
      climb.retractFront();
      climb.retractRear();
    } else {
      climb.rearOff();
      climb.frontOff();
    }


  }

  @Override
  public void disabledInit() {
    lightring.set(Value.kOff);
  }
}
