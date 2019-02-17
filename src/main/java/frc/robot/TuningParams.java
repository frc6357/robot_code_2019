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
    public static final double driveJoystickCoefficient   = -1.0;

    // **************
    // Lift Subsystem
    // **************

    public static final boolean ElevatorPosition0        = false; // Elevator Position for intake
    public static final boolean ElevatorPosition1        = false; // Elevator Postion for level 1
    public static final boolean ElevatorPosition2        = true;  // Elevator Position for level 2
    public static final boolean ElevatorPosition3        = true;  // Elevator Position for level 3

    public static final double ArmPosition0              = 0.0;   // Arm Positiom for level 0
    public static final double ArmPositionHatch1         = 5.0;   // Hatch Position for level 1
    public static final double ArmPositionHatch2         = 5.0;   // Hatch Position for level 2
    public static final double ArmPositionHatch3         = 50.0;  // Hatch Position for level 3
    public static final double ArmPostionCargo1          = 10.0;   // Cargo Position for level 1
    public static final double ArmPositionCargo2         = 10.0;   // Cargo Position for level 2
    public static final double ArmPositionCargo3         = 55.0;  // Cargo Postion for level 3

    // TODO: Tune lift arm PID coefficients.
    public static final double LiftArmPValue           = 0.005;
    public static final double LiftArmIValue           = 0.00005;
    public static final double LiftArmDValue           = 0;
    public static final double LiftArmToleranceValue   = 0;

    // ********************************************
    // Cargo Intake Subsystem (Intake and conveyor)
    // ********************************************
    public static final double intakeArmPValue           = 0.005;
    public static final double intakeArmIValue           = 0.00005;
    public static final double intakeArmDValue           = 0;
    public static final double intakeArmToleranceValue   = 0;

    // The speed of the intake and transfer roller motors when enabled.
    public static final double intakeTransferMotorSpeed  = 0.25;
    public static final double intakeIngestMotorSpeed    = 0.5;
    public static final double intakeArmMotorSpeed       = 0.25;

    // The angle (in degrees from the stowed position) to move the intake arm to
    // when deployed.
    //  TODO: Tune intake arm angle to the correct value.
    public static final double intakeArmDeployedAngle    = 90.0;
    public static final double intakeArmStowedAngle      = 0.0;

    // The triggered states of the ingest and transfer proximity sensors.
    // TODO: Set these states according to the way the sensor is wired.
    public static final boolean intakeIngestDetectState   = true;
    public static final boolean intakeTransferDetectState = true;

    // **************************************
    // Cargo Deploy Subsystem (The "Octopus")
    // **************************************

    // The triggered state of the cargo detect sensor.
    // TODO: Set this state according to the way the sensor is wired.
    public static final boolean octopusCargoDetectState  = true;

    // The fixed speed at which the octopus motor will run when turned on.
    // TODO: Set this speed appropriately.
    public static final double octopusMotorSpeed         = 0.25;
}
