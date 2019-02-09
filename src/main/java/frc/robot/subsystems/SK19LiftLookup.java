package frc.robot.subsystems;

/**
 *  This is the lookup file that is required for the Elevator positon and and Arm angle and has only tbe default constructor.
 */
public class SK19LiftLookup
{
    public final boolean ElevatorUp;
    public final double armAngle;

    /**
     *  This intializes the required elevator position and the required arm Angle for the set thing that it's set for
     *  @param ElevatorPosition
     *      - Type: Boolean
     *      - If true will be read and will set to the top position if false it will go to the bottom
     *  @param ArmAngle
     *      - Type: Double
     *      - This sets the arm angle for the system that it's being set for
     */
    public SK19LiftLookup(Boolean ElevatorPosition, double ArmAngle)
    {
        ElevatorUp = ElevatorPosition;
        armAngle = ArmAngle;
    }
}