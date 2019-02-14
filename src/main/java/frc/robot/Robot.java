/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.subsystems.base.BaseGroveIRProximitySensor;
import frc.robot.subsystems.base.BaseLimitSensor;
import frc.robot.subsystems.base.BaseProximitySensor;
import frc.robot.subsystems.base.BaseTankDrive;
import frc.robot.subsystems.base.BaseTankDrive2Motor;
import frc.robot.subsystems.base.BaseTankDrive3Motor;
//import frc.robot.subsystems.base.SPIEncoderAMT203V;
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
  private double driveLeft;
  private double driveRight;
  public static OI oi;
  public static UsbCamera camera;
  public static boolean cameraPrev = false;
  public static MjpegServer Server;
  private int m_DisplayUpdateCounter = 0;
  //public static SPIEncoderAMT203V intakeArmEncoder;

  public static BaseTankDrive BaseDrive = new BaseTankDrive3Motor();
  public static SmoothDrive   teleopDrive = new SmoothDrive(BaseDrive, Ports.driveMaxAccelForward, Ports.driveMaxAccelBackwards);
  public static BaseLimitSensor testSensor = new BaseGroveIRProximitySensor(9);

  // This is the number of periodic callbacks to skip between each update
  // of the smart dashboard data. With a value of 10, we update the smart dashboard
  // 5 times per second (based on a 20mS periodic callback).
  private static final int DASHBOARD_UPDATE_INTERVAL = 10;

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

    camera=CameraServer.getInstance().startAutomaticCapture("Driver Front Camera", 0);
    //cameraRear=CameraServer.getInstance().startAutomaticCapture("Rear Camera", 1);
    //cameraRear.setResolution(640, 480);
    camera.setResolution(240, 240);
    camera.setFPS(15);
    //Server = new MjpegServer("cameraServer", 1);

    // TODO: Need to implement to call absolute encoder
    //intakeArmEncoder = new SPIEncoderAMT203V(Ports.intakeEncoderSPI, 1024);
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
    UpdateSmartDashboard(oi.getMode());
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
    oi.setMode(OI.Mode.NORMAL);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    // This year, we handle autonomous and teleop exactly the same way.
    teleopPeriodic();
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

    driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive); // Retrieves the status of all buttons and joysticks
    driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive);

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
        //CameraServer camServer = CameraServer.getInstance();
        //camServer.addServer(Server);
        oi.setMode(OI.Mode.TEST);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
    double driveLeft, driveRight;

    //System.out.println("Encoder Value " + intakeArmEncoder.get());

    Scheduler.getInstance().run();

    driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive); // Retrieves the status of all buttons and joysticks
    driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive);

    BaseDrive.setLeftSpeed(driveLeft); // Listens to input and drives the robot
    BaseDrive.setRightSpeed(driveRight);

    UpdateSmartDashboard(OI.Mode.TEST);
    //System.out.println("Intake Arm SPI Encoders" + intakeArmEncoder.get());
    //if (OI.buttonCameraShifter.get() && !cameraPrev)
    //{
    //    //NetworkTableInstance.getDefault().getTable("").//.putString("Camera Selection", cameraRear.getName());
    //    //Server.setSource(cameraRear);
    //    System.out.println("This should be rear camera");
    //}
    //else if (!OI.buttonCameraShifter.get() && cameraPrev)
    //{
    //    //Server.setSource(camera);
    //    System.out.println("This should be front camera");
    //}
    //cameraPrev = OI.buttonCameraShifter.get();
  }

  void UpdateSmartDashboard(OI.Mode mode)
  {
    m_DisplayUpdateCounter++;

    if((m_DisplayUpdateCounter % DASHBOARD_UPDATE_INTERVAL) != 0)
      return;

    switch(mode)
    {
      case NONE: break;

      case TEST:
      {
        // TODO: Send back additional test mode information for the smart dashboard.
        SmartDashboard.putNumber("Left Commanded Speed", driveLeft);
        SmartDashboard.putNumber("Right Commanded Speed", driveRight);
        SmartDashboard.putNumber("Left Actual Speed", BaseDrive.getLeftSpeed());
        SmartDashboard.putNumber("Right Actual Speed", BaseDrive.getRightSpeed());
        SmartDashboard.putNumber("Left Encoder Raw", BaseDrive.getLeftEncoderRaw());
        SmartDashboard.putNumber("Right Encoder Raw", BaseDrive.getRightEncoderRaw());
        SmartDashboard.putNumber("Left Encoder Dist", BaseDrive.getLeftEncoderDistance());
        SmartDashboard.putNumber("Right Encoder Dist", BaseDrive.getRightEncoderDistance());
        //SmartDashboard.putNumber("Intake Arm SPI Encoders", intakeArmEncoder.get());
        SmartDashboard.putBoolean("Test Grove Sensor", testSensor.getIsTriggered());

        //SmartDashboard.putNumber("Front RangeFinder Distance mm", forwardRange.getDistanceMm());
        //SmartDashboard.putNumber("Front RangeFinder Voltage", forwardRange.getVoltage());
      }
      break;

      case MANUAL:
      {
        // TODO: Send back top level info to the smart dashboard in override mode.
      }
      break;

      case NORMAL:
      {
        // TODO: Send back top level info to the smart dashboard in normal mode.
      }
      break;
    }
  }
}

