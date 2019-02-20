package frc.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting timed delays. This is intended for use in
 * command groups where a delay is required between adjacent commands.
 */
public class DelayCommand extends Command
{
    private int msDelay;
    private int msToGo;
    private final int msPerPeriodic = 20;

    public DelayCommand(int msDelay)
    {
        this.msDelay = msDelay;
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
        msToGo = msDelay;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        msToGo -= msPerPeriodic;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return (msToGo <= 0);
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
