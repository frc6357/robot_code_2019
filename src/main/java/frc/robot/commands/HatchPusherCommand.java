package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the command sequence used to release a hatch.
 */
public class HatchPusherCommand extends Command
{
    private OI.Mode mode;
    private boolean extended;

    public HatchPusherCommand(OI.Mode mode, boolean extended)
    {
        requires(Robot.Lift);
        this.mode = mode;
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        Robot.Lift.setHatchPusher(extended);
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
