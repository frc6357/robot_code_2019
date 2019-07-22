package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A dummy command which can be excuted merely to cause any existing command on the intake to cancel.
 */
public class IntakeDummyCommand extends Command
{
    private OI.Mode mode;

    public IntakeDummyCommand(OI.Mode mode)
    {
        requires(Robot.Intake);
        requires(Robot.Lift);
        this.mode   = mode;
    }

    protected void initialize()
    {
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;
            
        // TODO: Code the TestToggleClimbTilt command!
    }

    protected void execute()
    {
        if(mode != Robot.oi.getMode())
            return;
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
