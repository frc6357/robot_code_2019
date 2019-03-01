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
import frc.robot.subsystems.base.BaseTankDrive;
import frc.robot.subsystems.base.BaseTankDrive2Motor;
import frc.robot.subsystems.SmoothDrive;
import frc.robot.utils.ScaledEncoder;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;

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

  public static BaseTankDrive BaseDrive = new BaseTankDrive2Motor();
  public static SmoothDrive   teleopDrive = new SmoothDrive(BaseDrive, Ports.driveMaxAccelForward, Ports.driveMaxAccelBackwards);
 
  // Create basic controllers for every subsystem motor on the robot. We're going to control
  // these directly purely to allow us to verify that they are connected and operating
  // correctly before we start trying to debug the main robot application.
  public static WPI_VictorSPX IntakeArmMotor    = new WPI_VictorSPX(Ports.intakeArmMotor);
  public static WPI_VictorSPX IntakeRollerMotor = new WPI_VictorSPX(Ports.intakeRollerMotor);
  public static CANSparkMax   LiftArmMotor      = new CANSparkMax(Ports.armRotateMotor, MotorType.kBrushless);
  public static WPI_TalonSRX  LiftRollerMotor   = new WPI_TalonSRX(Ports.octopusMotor);

  // Assorted encoders and sensors.
  public static ScaledEncoder IntakeArmEncoder  = new ScaledEncoder(Ports.intakeArmEncoderA, Ports.intakeArmEncoderB,
                                                                    Ports.intakeArmEncoderPulsesPerRev, Ports.intakeArmEncoderDiameter);
  public static CANEncoder LiftArmEncoder    = new CANEncoder(LiftArmMotor);

  // This is the number of periodic callbacks to skip between each update
  // of the smart dashboard data. With a value of 5, we update the smart dashboard
  // 10 times per second (based on a 20mS periodic callback).
  private static final int DASHBOARD_UPDATE_INTERVAL = 5;

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
    camera.setResolution(240, 240);
    camera.setFPS(15);

    IntakeArmEncoder.reset();
    LiftArmEncoder.setPosition(0.0);
    LiftArmEncoder.setPositionConversionFactor(2*((1/54) * 90));
  }


    /**
     * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit()
    {
        BaseDrive.setLeftSpeed(0);
        BaseDrive.setRightSpeed(0);
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
    UpdateSmartDashboard();
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

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
    double driveLeft, driveRight;
    double intakeRoller, liftRoller;
    double intakeArm, liftArm;

    Scheduler.getInstance().run();

    driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive); // Retrieves the status of all buttons and joysticks
    driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive);

    BaseDrive.setLeftSpeed(driveLeft); // Listens to input and drives the robot
    BaseDrive.setRightSpeed(driveRight);
    
    // Set each accessory motor speed based on one of the operator joystick values.
    intakeRoller = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickLX);
    intakeArm    = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickLY);
    liftRoller   = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickRX);
    liftArm      = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickRY);

    IntakeArmMotor.set(intakeArm);
    IntakeRollerMotor.set(intakeRoller);
    LiftArmMotor.set(liftArm);
    LiftRollerMotor.set(liftRoller);
  }

  void UpdateSmartDashboard()
  {
    m_DisplayUpdateCounter++;

    if((m_DisplayUpdateCounter % DASHBOARD_UPDATE_INTERVAL) != 0)
      return;

    SmartDashboard.putNumber("Left Actual Speed", BaseDrive.getLeftSpeed());
    SmartDashboard.putNumber("Right Actual Speed", BaseDrive.getRightSpeed());
    SmartDashboard.putNumber("Intake Roller Speed", IntakeRollerMotor.get());
    SmartDashboard.putNumber("Intake Arm Speed", IntakeArmMotor.get());
    SmartDashboard.putNumber("Intake Angle", IntakeArmEncoder.getAngleDegrees());
    SmartDashboard.putNumber("Intake Encoder Raw", IntakeArmEncoder.get());
    SmartDashboard.putNumber("Lift Roller Speed", LiftRollerMotor.get());
    SmartDashboard.putNumber("Lift Arm Speed", LiftArmMotor.get());
    //SmartDashboard.putNumber("Lift Angle", LiftArmEncoder.getAngleDegrees());
    SmartDashboard.putNumber("Lift Encoder Raw", LiftArmEncoder.getPosition());
  }
}

