/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import frc.robot.subsystems.base.BaseMotorizedArm;
import frc.robot.subsystems.SK19CargoIntake;
import frc.robot.subsystems.SK19Drive;
import frc.robot.subsystems.SK19Lift;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private double driveLeft;
    private double driveRight;
    public static OI oi;
    public static UsbCamera camera;
    public static boolean cameraPrev = false;
    public static MjpegServer Server;
    private int m_DisplayUpdateCounter = 0;

    public static SpeedController armMotorController;
    public static BaseMotorizedArm armSystem;

    public static SK19Drive Drive = new SK19Drive();
    public static SK19CargoIntake Intake = new SK19CargoIntake();
    public static SK19Lift Lift = new SK19Lift();

    // This is the number of periodic callbacks to skip between each update
    // of the smart dashboard data. With a value of 10, we update the smart
    // dashboard
    // 5 times per second (based on a 20mS periodic callback).
    private static final int DASHBOARD_UPDATE_INTERVAL = 10;

    private boolean PIDSEnabled;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        Drive.baseDrive.setLeftSpeed(0);
        Drive.baseDrive.setRightSpeed(0);

        PIDSEnabled = false;

        // Initialize the operator interface.
        oi = new OI();

        camera = CameraServer.getInstance().startAutomaticCapture("Driver Front Camera", 0);
        camera.setResolution(240, 240);
        camera.setFPS(15);
    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
        Drive.baseDrive.setLeftSpeed(0);
        Drive.baseDrive.setRightSpeed(0);
        Intake.RollerArm.disable();
        Lift.RobotArmAngled.disable();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     *
     * <p>
     * This runs after the mode specific periodic functions, but before LiveWindow
     * and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        UpdateSmartDashboard(oi.getMode());
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable chooser
     * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString line to get the
     * auto name from the text box below the Gyro
     *
     * <p>
     * You can add additional auto modes by adding additional comparisons to the
     * switch structure below with additional strings. If using the SendableChooser
     * make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        oi.setMode(OI.Mode.MANUAL);

        if (!PIDSEnabled) {
            Intake.RollerArm.enable();
            Lift.RobotArmAngled.enable();
            PIDSEnabled = true;
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        // This year, we handle autonomous and teleop exactly the same way.
        teleopPeriodic();
    }

    @Override
    public void teleopInit() {
        // Do anything needed here when autonomous mode exits.
        // NB: In the 2019 game we will NOT be using autonomous code so this function
        // must NOT do anything to change the state of the robot!

        if (!PIDSEnabled) {
            Intake.RollerArm.enable();
            Lift.RobotArmAngled.enable();
            PIDSEnabled = true;
        }

        oi.setMode(OI.Mode.MANUAL);
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        OI.Mode mode = oi.getMode();

        Scheduler.getInstance().run();

        // Driver updates
        driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive);
        driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive);

        Drive.Drive.setLeftSpeed(driveLeft);
        Drive.Drive.setRightSpeed(driveRight);
        Drive.Drive.SmoothDrivePeriodic();

        // Operator updates. Left Y joystick controls the arm angle in MANUAL mode.
        if(mode == OI.Mode.MANUAL)
        {
            double armPosAngle;
            double operatorLeftY;

            operatorLeftY = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickLY, true);
            armPosAngle = Lift.RobotArmAngled.getArmSetpoint();
            if(operatorLeftY > 0.9)
                armPosAngle += 1.0;
            if(operatorLeftY < -0.9)
                armPosAngle -= 1.0;
            armPosAngle = Math.min(TuningParams.LiftArmAngleMax, armPosAngle);
            armPosAngle = Math.max(TuningParams.LiftArmAngleMin, armPosAngle);
            Lift.RobotArmAngled.setSetpoint(armPosAngle);
        }

        // Housekeeping
        Lift.periodic();
    }

    /**
     * This function is called on entry into test mode.
     */
    @Override
    public void testInit()
    {
        Intake.RollerArm.enable();

        // TODO: Add this when we want to enable the PID controller on the arm motor.
        //Lift.RobotArmAngled.enable();

        oi.setMode(OI.Mode.TEST);
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
        double driveLeft, driveRight;
        double operatorLeftY, operatorRightY;

        Scheduler.getInstance().run();

        driveLeft = oi.getDriverJoystickValue(Ports.OIDriverLeftDrive); 
        driveRight = oi.getDriverJoystickValue(Ports.OIDriverRightDrive);

        operatorRightY = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickRY, true);
        operatorLeftY = oi.getOperatorJoystickValue(Ports.OIOperatorJoystickLY, true);

        Drive.baseDrive.setLeftSpeed(driveLeft); // Listens to input and drives the robot
        Drive.baseDrive.setRightSpeed(driveRight);

        UpdateSmartDashboard(OI.Mode.TEST);

        Lift.cargoSystem(operatorRightY);
        Lift.testSetArmPositionMotorSpeed(operatorLeftY / TuningParams.LiftArmTestSpeedDivider);

        // Perform housekeeping.
        Lift.periodic();
    }

    void UpdateSmartDashboard(OI.Mode mode) {
        m_DisplayUpdateCounter++;

        if ((m_DisplayUpdateCounter % DASHBOARD_UPDATE_INTERVAL) != 0)
            return;

        switch (mode) {
        case NONE:
            break;

        case TEST: {
            // TODO: Send back additional test mode information for the smart dashboard.
            SmartDashboard.putNumber("Left Commanded Speed", driveLeft);
            SmartDashboard.putNumber("Right Commanded Speed", driveRight);
            SmartDashboard.putNumber("Left Actual Speed", Drive.baseDrive.getLeftSpeed());
            SmartDashboard.putNumber("Right Actual Speed", Drive.baseDrive.getRightSpeed());
            SmartDashboard.putNumber("Left Encoder Raw", Drive.baseDrive.getLeftEncoderRaw());
            SmartDashboard.putNumber("Right Encoder Raw", Drive.baseDrive.getRightEncoderRaw());
            SmartDashboard.putNumber("Left Encoder Dist", Drive.baseDrive.getLeftEncoderDistance());
            SmartDashboard.putNumber("Right Encoder Dist", Drive.baseDrive.getRightEncoderDistance());
            // SmartDashboard.putBoolean("Spinning Forwards",
            // oi.buttonOperatorRightBumper.get());

            // PID for intake arm
            SmartDashboard.putNumber("P", TuningParams.intakeArmPValue);
            SmartDashboard.putNumber("I", TuningParams.intakeArmIValue);
            SmartDashboard.putNumber("D", TuningParams.intakeArmDValue);
            SmartDashboard.putNumber("Tolerance", TuningParams.intakeArmToleranceValue);
            // SmartDashboard.putNumber("Set Point", Intake.RollerArm.getArmSetpoint());
            // SmartDashboard.putNumber("Encoder Angle",
            // Intake.RollerArm.armEncoder.getAngleDegrees());
            // SmartDashboard.putNumber("Position", Intake.RollerArm.getArmPosition());
            // SmartDashboard.putNumber("Intake Arm Motor Speed", Intake.ArmMotor.get());

            // SmartDashboard.putNumber("Front RangeFinder Distance mm",
            // forwardRange.getDistanceMm());
            // SmartDashboard.putNumber("Front RangeFinder Voltage",
            // forwardRange.getVoltage());
        }
            break;

        case MANUAL: {
            // TODO: Send back top level info to the smart dashboard in override mode.
        }
            break;

        case NORMAL: {
            // TODO: Send back top level info to the smart dashboard in normal mode.
        }
            break;
        }
    }
}
