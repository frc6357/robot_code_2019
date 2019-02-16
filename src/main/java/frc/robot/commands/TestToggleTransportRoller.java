package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the command sequence used to toggle the transport roller
 * motor on and off in test mode
 */
public class TestToggleTransportRoller extends Command
{
    OI.Mode mode;

    public TestToggleTransportRoller(OI.Mode mode)
    {
        this.mode = mode;
    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        // TODO: Code the TestToggleTransportRoller command!
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
