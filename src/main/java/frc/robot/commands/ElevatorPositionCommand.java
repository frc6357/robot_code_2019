package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the setting of the Elevators position
 */
public class ElevatorPositionCommand extends Command
{
    private OI.Mode mode;
    private boolean up;

    /**
     *
     * @param Up sets the Elevator to up or down based on a boolean variable
     */
    public ElevatorPositionCommand(OI.Mode mode, boolean up)
    {
        requires(Robot.Lift);
        this.mode = mode;
        this.up   = up;
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        Robot.Lift.SetElevatorPosition(up);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return true;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
    }
}
