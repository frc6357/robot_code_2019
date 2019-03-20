/**
 *
 */
package frc.robot;

/**
 *
 * This class defines all tunable software parameters related to the robot.
 * Note that any hardware-connection-related parameters must be in Ports.java.
 * Values in this file include PID coefficients, filter settings, etc.
 *
 * @author DW
 *
 */
public class TuningParams
{
    // *****************
    // Drive Subsystem
    // *****************

    // These values are the maximum motors speed change (where the range is [-1, 1])
    // per 20mS. These give us a 0.33 second ramp from stopped to maximum speed in
    // either direction. Tune these as required.
    public static final double driveMaxAccelForward       = 0.1;
    public static final double driveMaxAccelBackwards     = 0.1;

    // Set this to determine the maximum speed of the robot. 0.5 means that
    // full joystick will drive the motors at 50% of their maximum rate.
    public static final double driveJoystickCoefficient   = 1.0;

    // **************
    // Lift Subsystem
    // **************

    public static final boolean ElevatorPosition0        = false; // Elevator Position for intake
    public static final boolean ElevatorPosition1        = false; // Elevator Postion for level 1
    public static final boolean ElevatorPositionHatch2   = false; // Elevator Position for level 2
    public static final boolean ElevatorPositionCargo2   = true;  // Elevator Position for cargo level 2
    public static final boolean ElevatorPosition3        = true;  // Elevator Position for level 3

    public static final double ArmPosition0              = 0.0;   // Arm Positiom for level 0
    public static final double ArmPositionHatch1         = 11.0;  // Hatch Position for level 1
    public static final double ArmPositionHatch2         = 45.0;  // Hatch Position for level 2
    public static final double ArmPositionHatch3         = 45.0;  // Hatch Position for level 3
    public static final double ArmPostionCargo1          = 16.0;  // Cargo Position for level 1
    public static final double ArmPositionCargo2         = 50.0;  // Cargo Position for level 2
    public static final double ArmPositionCargo3         = 50.0;  // Cargo Postion for level 3

    public static final String liftPositionStow          = "STOW";
    public static final String liftPositionLower         = "LOWER";
    public static final String liftPositionMiddle        = "MIDDLE";
    public static final String liftPositionUpper         = "UPPER";

    // PID values for the arm
    public static final double LiftArmPValue             = 0.01;
    public static final double LiftArmIValue             = 0.0;
    public static final double LiftArmDValue             = 0;
    public static final double LiftArmToleranceValue     = 0;

    public static final boolean LiftArmInvertMotor       = false;

    // In manual override mode, these are the angle setpoint limits for the arm.
    public static final double LiftArmAngleMax           = 120.0;
    public static final double LiftArmAngleMin           = 0.0;

    // In test mode, when we set the arm motor speed directly from the joystick, this
    // is the value we divide the joystick by before setting the motor speed.
    public static final double LiftArmTestSpeedDivider   = 2.0;

    // ********************************************
    // Cargo Intake Subsystem (Intake and conveyor)
    // ********************************************
    public static final double intakeArmPValue           = 0.005;
    public static final double intakeArmIValue           = 0.0;
    public static final double intakeArmDValue           = 0;
    public static final double intakeArmToleranceValue   = 0;

    public static final boolean intakeArmInvertMotor     = true;

    // The speed of the intake and transfer roller motors when enabled.
    public static final double intakeTransferMotorSpeed  = 1.0;
    public static final double intakeIngestMotorSpeed    = 1.0;
    public static final double intakeArmMotorSpeed       = 0.25;

    public static final double cargoIntakeDownLimit      = 1.0;
    public static final double armCargoDeadband          = 5.0;

    // The angle (in degrees from the stowed position) to move the intake arm to
    // when deployed.
    // TODO: Set deployed back to 120 when working again, set to 100 for now as a safety measure
    public static final double intakeArmDeployedAngle    = 90.0;
    public static final double intakeArmStowedAngle      = 0.0;

    public static final double intakeRollerFullSpeed     = 1.0;
    public static final double intakeRollerStop          = 0.0;

    // **************************************
    // Cargo Deploy Subsystem (The "Octopus")
    // **************************************

    // The triggered state of the cargo detect sensor.
    // TODO: Set this state according to the way the sensor is wired.
    public static final boolean octopusCargoDetectState  = true;

    // The fixed speed at which the octopus motor will run when turned on.
    public static final double cargoPullInWaitTime       = 1.0;
    public static final double octopusMotorSpeed         = -1.0;

    // ***************************************
    // Climb System
    // ***************************************

    public static final double climbWedgeServoLockPosition = 0.0;

    public static final double climbWedgeServoUnlockPosition = 1.0;
}
