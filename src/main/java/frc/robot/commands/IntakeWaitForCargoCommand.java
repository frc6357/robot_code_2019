package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command which executes until cargo either detected in or removed from the intake area.
 */
public class IntakeWaitForCargoCommand extends Command
{
    private OI.Mode mode;
    
    /**
     *
     * @param mode - the operating mode in which this command must run.
     */
    public IntakeWaitForCargoCommand(OI.Mode mode)
    {
        requires(Robot.Lift);

        this.mode       = mode;
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
        Robot.Lift.setBallOveride(!Robot.Lift.ballOveride);
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
