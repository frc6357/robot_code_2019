package frc.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A class which implements a command that does absolutely nothing. This
 * may sound silly but it's useful when switching modes and making some
 * buttons inactive.
 */
public class NullCommand extends Command
{

    /**
     *
     * @param
     */
    public NullCommand()
    {
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {

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
