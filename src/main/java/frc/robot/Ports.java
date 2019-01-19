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
    // MXP is not available if we attach the IMU, limiting us to 10 DIOs.
    //
    // Pneumatics:
    //
    // The assumption is that all pneumatics will use single solenoid operation with springs
    // fitted to cylinders to pull the shaft back to the resting position when the PCM channel 
    // is not activated. Naming here indicates the operation when the relevant PCM channel is
    // activated with the resting case being the opposite. For example, "driveGearShiftHigh" is
    // activated to select high gear in the drive system. When not activated, low gear is selected.
    // Does this make sense? Some would say so.
    //
    // Free RoboRIO and PCM ports:
    //
    // TODO: Fill this in when we see what's left over for last minute use.
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

    // *************
    // Hatch Gripper
    // *************

    // TODO: Verify whether or not the second pneumatic cylinder to move the gripper
    //       in and out will be implemented. We've included it here.
    public static final int hatchGripperPCM              = pcm1;

    public static final int hatchGripperOut              = 1;    // PCM 1 output 1
    public static final int hatchGripperLock             = 2;    // PCM 1 output 2

    public static final int hatchContactSwitch           = 4;    // DIO 4

    // **************
    // Lift Subsystem
    // **************

    //
    // Elevator components
    //
    public static final int elevatorPCM                  = pcm1;
    public static final int elevatorUp                   = 3;    // PCM 1 output 3

    // TODO: Check elevator position sensor information - switches or ultrasonic?
    public static final int elevatorLimitUp              = 5;    // DIO input 5
    public static final int elevatorLimitDown            = 6;    // DIO input 6

    //
    // Arm components
    //
    public static final int armRotateMotor               = 21;   // CAN ID 21

    // TODO: Determine if we're using a QEI or variable resistor sensor
    //       to measure the arm angle. We assume QEI here (2 connections)
    public static final int armEncoderA                  = 7;    // DIO input 7
    public static final int armEncoderB                  = 8;    // DIO input 8

    // TODO: Verify whether we connect these to roboRIO or directly to the motor
    //       controller.
    public static final int armLimitTop                  = 9;    // DIO input 9
    public static final int armLimitBottom               = 10;   // DIO input 10

    // TODO: Define this information once climb design is clearer.

    // ********************************************
    // Cargo Intake Subsystem (Intake and conveyor)
    // ********************************************
    public static final int intakeIngestMotor            = 20;   // CAN ID 20
    public static final int intakeArmMotor               = 25;   // CAN ID 25

    // Switch to detect presence of ball immediately after ingest.
    public static final int intakeIngestDetect           = 11;   // DIO input 11

    public static final int intakeTransferMotorLeft      = 22;   // CAN ID 22
    public static final int intakeTransferMotorRight     = 23;   // CAN ID 23

    // This is a switch detecting the presence of cargo between the transfer
    // rollers.
    public static final int intakeTransferDetect         = 12;   // DIO input 12

    public static final int intakeTrackMotor             = 24;   // CAN ID 24

    // **************************************
    // Cargo Deploy Subsystem (The "Octopus")
    // **************************************
    public static final int octopusMotor                 = 30;   // CAN ID 30
    public static final int octopusCargoDetect           = 13;   // DIO input 13

    // ***************
    // Climb Subsystem
    // ***************
    public static final int climbPCM                     = pcm1; 

    public static final int climbRailDeploy              = 4;    // PCM 1 channel 4
    public static final int climbTiltDeploy              = 5;    // PCM 1 channel 5

    public static final int climbMotor                   = 40;   // CAN ID 40

    public static final int climbStopSwitch              = 14;   // DIO input 14

    // ******************
    // Additional sensors
    // ******************

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
    public static final int OIDriverJoystick            = 0;

    public static final int OIDriverLeftDrive           = 1; // Left Joystick Y
    public static final int OIDriverRightDrive          = 5; // Right Joystick Y
    public static final int OIDriverSlow                = 5; // Left bumper

    public static final int IODriverGearSelectLow       = 1; // Button A
    public static final int IODriverGearSelectHigh      = 4; // Button Y

    // *********************
    // Operator Controller
    // *********************
    public static final int OIOperatorJoystick          = 1;

    // TODO: Define what the operator controls are to be.
}
