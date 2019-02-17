/**
 *
 */
package frc.robot;

/**
 *
 * This class defines the connections of all actuators and controllers to the
 * RoboRio and allocation of driver and operator controller user interface
 * joysticks and buttons. It is intended to be used by all classes which would
 * otherwise hardcode these values on the understanding that using a
 * decscriptive name is very much clearer than an opaque number.
 *
 * @author DW
 *
 */
public class Ports
{
    //
    // RoboRio and PCM I/O connections for robot motors and actuators
    //

    // Resource limits:
    //
    // Each PCM provides 8 channels where 2 are needed per double-solenoid, 1 for single.
    // RoboRIO has a total of 25 DIO pins, 10 on the base, 15 on the MXP. Pullup/down varies on some pins!
    //
    // All standard DIOs (on RoboRIO and MXP) are pulled up to 3.3V.
    //

    // ***************************
    // Pneumatic Control Module(s)
    // ***************************
    public static final int pcm1                        = 1; // CAN ID 1

    // *****************
    // Drive Subsystem
    // *****************
    public static final int driveLeftFrontMotor          = 11;   // CAN ID 11
    public static final int driveLeftRearMotor           = 10;   // CAN ID 10

    public static final int driveRightFrontMotor         = 16;   // CAN ID 16
    public static final int driveRightRearMotor          = 12;   // CAN ID 12

    public static final int driveGearShiftPCM            = pcm1;
    public static final int driveGearShiftHigh           = 0;    // PCM 1 output 0

    public static final int driveLeftEncoderA            = 2;    // DIO input 2
    public static final int driveLeftEncoderB            = 3;    // DIO input 3

    public static final int driveRightEncoderA           = 0;    // DIO input 0
    public static final int driveRightEncoderB           = 1;    // DIO input 1

    public static final int driveEncoderPulsesPerRotation = 256;
    public static final double driveWheelDiameterInches   = 6.0;

    // *************
    // Hatch Gripper
    // *************

    public static final int hatchGripperPCM              = pcm1;

    // The hatch gripper and deploy mechanism uses double solenoids so we need
    // two PCM channels per piston.
    public static final int hatchGripperOut              = 2;    // PCM 1 output 2
    public static final int hatchGripperIn               = 4;    // PCM 1 output 4
    public static final int hatchGripperLock             = 3;    // PCM 1 output 3
    public static final int hatchGripperUnlock           = 5;    // PCM 1 output 5

    public static final int hatchContactSwitch           = 4;    // DIO 4

    // **************
    // Lift Subsystem
    // **************

    //
    // Elevator components
    //
    public static final int elevatorPCM                  = pcm1;
    public static final int elevatorUp                   = 1;    // PCM 1 output 6

    public static final int elevatorProximityUp          = 5;    // DIO input 5
    public static final int elevatorProximityDown        = 6;    // DIO input 6

    //
    // Arm components
    //
    public static final int armRotateMotor               = 21;   // CAN ID 21

    public static final int armEncoderA                  = 14;    // DIO input 14
    public static final int armEncoderB                  = 15;    // DIO input 15
    public static final double armEncoderDiameter  = 0.25;
    public static final int armEncoderPulsesPerRev       = 1024;

    // TODO: Verify whether we connect these to roboRIO or directly to the motor
    //       controller.
    public static final int armLimitTop                  = 7;    // DIO input 7
    public static final int armLimitBottom               = 10;   // DIO input 10

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

    // ********************************************
    // Cargo Intake Subsystem (Intake and conveyor)
    // ********************************************
    public static final int intakeRollerMotor            = 20;   // CAN ID 20
    public static final int intakeArmMotor               = 25;   // CAN ID 25

    public static final int intakeArmEncoderA            = 8;   // DIO input 8
    public static final int intakeArmEncoderB            = 9;   // DIO input 9
    public static final double intakeArmEncoderDiameter  = 0.25;
    public static final int intakeEncoderPulsesPerRev    = 1024;

    // The resolution of the encoder attached to the intake arm.
    public static final int intakeArmEncoderPulsesPerRev = 1024;

    // IR proximity detector to detect presence of ball immediately after ingest.
    public static final int intakeIngestDetect           = 11;   // DIO input 11

    // TODO: The mechanism to move the cargo from the initial intake to the
    // "octopus" must be finalized! The following definitions are likely
    // incorrect.
    public static final int intakeTransferMotorLeft      = 22;   // CAN ID 22
    public static final int intakeTransferMotorRight     = 23;   // CAN ID 23

    // This is an IR proximity sensor detecting the presence of cargo between the transfer
    // rollers.
    public static final int intakeTransferDetect         = 12;   // DIO input 12

    // **************************************
    // Cargo Deploy Subsystem (The "Octopus")
    // **************************************
    public static final int octopusMotor                 = 30;   // CAN ID 30
    public static final int octopusCargoDetect           = 13;   // DIO input 13

    // ***************
    // Climb Subsystem
    // ***************
    public static final int climbPCM                     = pcm1;

    public static final int climbTiltDeploy              = 7;    // PCM 1 channel 7

    public static final int climbMotor                   = 40;   // CAN ID 40

    // ******************
    // Additional sensors
    // ******************

    // TODO: Will these be fitted?
    public static final int driveFrontRangefinder        = 0;    // ADC channel 0
    public static final int driveRearRangefinder         = 1;    // ADC channel 1

    //
    // Driver's and operator's OI channel assignments
    //

    // See the IO subsystem specification for a graphic showing button
    // and axis IDs for the Logitech F310 gamepads in use.

    // ********************
    // Drivers Controller
    // ********************
    public static final int OIDriverJoystick             = 0;

    public static final int OIDriverLeftDrive            = 1;    // Left Joystick Y
    public static final int OIDriverRightDrive           = 5;    // Right Joystick Y
    public static final int OIDriverSlow                 = 5;    // Left bumper

    public static final int IODriverGearShift            = 6;    // Right Bumper

    public static final int OIDriverCameraSwitcher       = 2;     // Button B

    // *********************
    // Operator Controller
    // *********************
    public static final int OIOperatorJoystick           = 1;

    public static final int OIOperatorJoystickL          = 9;    // Left Joystick
    public static final int OIOperatorJoystickR          = 10;   // Right Joystick
    public static final int OIOperatorLeftBumper         = 5;    // Left Bumper
    public static final int OIOperatorRightBumper        = 6;    // Right Bumper

    public static final int OIOperatorJoystickARMPos     = 1;    // Left stick, X axis

    public static final int OIOperatorButtonA            = 1;    // A Button
    public static final int OIOperatorButtonB            = 2;    // B Button
    public static final int OIOperatorButtonY            = 4;    // Y Button
    public static final int OIOperatorButtonX            = 3;    // X Button

    public static final int OIOperatorTriggerJoystick    = 3;    // Left & Right Trigger

    public static final int OIOperatorJoystickLY         = 2;    // Up D-Pad, Joystick axis 2

    public static final int OIOperatorBack               = 7;    // Back/Select Button
    public static final int OIOperatorStart              = 8;    // Start Button
}
