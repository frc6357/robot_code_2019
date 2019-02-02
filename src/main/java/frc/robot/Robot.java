/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.subsystems.base.BaseTankDrive;
// TODO: Change this to BaseTankDrive2Motor for 2019 drivetrain.
import frc.robot.subsystems.base.BaseTankDrive3Motor;
import frc.robot.subsystems.base.RangefinderMB1013;
import frc.robot.subsystems.SmoothDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private double driveLeft;
  private double driveRight;
  public static OI oi;
  public static UsbCamera camera;
  public static UsbCamera cameraRear;
  private int m_DisplayUpdateCounter = 0;

  // TODO: This is configured to use the Torsion drive. Replace this with
  // BaseTankDrive2Motor when the 2019 drivetrain is available.
  public static BaseTankDrive BaseDrive = new BaseTankDrive3Motor();
  public static SmoothDrive   teleopDrive = new SmoothDrive(BaseDrive, Ports.driveMaxAccelForward, Ports.driveMaxAccelBackwards);
  public static RangefinderMB1013 forwardRange = new RangefinderMB1013(Ports.driveFrontRangefinder);
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    BaseDrive.setLeftSpeed(0);
    BaseDrive.setRightSpeed(0);

    // Initialize the operator interface.
    oi = new OI();

    // TODO: Set up any smartdashboard chooser options here.
    
    camera=CameraServer.getInstance().startAutomaticCapture("Testing", 0);
    cameraRear=CameraServer.getInstance().startAutomaticCapture("Rear Camera", 1);
    cameraRear.setResolution(150, 150);
    camera.setResolution(150, 150);
    cameraRear.setFPS(30);
  }


    /**
     * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit()
    {
        BaseDrive.setLeftSpeed(0);
        BaseDrive.setRightSpeed(0);

        // TODO: Do anything else needed to safe the robot when it is disabled.
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
  public void robotPeriodic()
  {
    SmartDashboard.putNumber("Left Commanded Speed", driveLeft);
    SmartDashboard.putNumber("Right Commanded Speed", driveRight);
    SmartDashboard.putNumber("Left Actual Speed", BaseDrive.getLeftSpeed());
    SmartDashboard.putNumber("Right Actual Speed", BaseDrive.getRightSpeed());
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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    teleopDrive.SmoothDrivePeriodic();
  }

  @Override
  public void teleopInit()
  {
    // Do anything needed here when autonomous mode exits.
    // NB: In the 2019 game we will NOT be using autonomous code so this function
    // must NOT do anything to change the state of the robot!
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
    

    Scheduler.getInstance().run();

    driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive, true); // Retrieves the status of all buttons and joysticks
    driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive, true);

    teleopDrive.setLeftSpeed(driveLeft);
    teleopDrive.setRightSpeed(driveRight);

    teleopDrive.SmoothDrivePeriodic();
  }

  /**
  * This function is called on entry into test mode.
  */
  @Override
  public void testInit()
  {
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
    double driveLeft, driveRight;

    Scheduler.getInstance().run();

    driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive, true); // Retrieves the status of all buttons and joysticks
    driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive, true);

    BaseDrive.setLeftSpeed(driveLeft); // Listens to input and drives the robot
    BaseDrive.setRightSpeed(driveRight);


    if(m_DisplayUpdateCounter % 10 == 0)
    {
        SmartDashboard.putNumber("Left Encoder Raw", BaseDrive.getLeftEncoderRaw());
        SmartDashboard.putNumber("Right Encoder Raw", BaseDrive.getRightEncoderRaw());
        SmartDashboard.putNumber("Left Encoder Dist", BaseDrive.getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Encoder Dist", BaseDrive.getRightEncoderDistance());
        SmartDashboard.putNumber("Front RangeFinder Distance mm", forwardRange.getDistanceMm());
        SmartDashboard.putNumber("Front RangeFinder Voltage", forwardRange.getVoltage());
    }
    m_DisplayUpdateCounter++;
  }
}

