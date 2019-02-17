package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the command sequence used to grab a hatch.
 */
public class GrabHatch extends Command
{
    OI.Mode mode;

    public GrabHatch()
    {
    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
        Robot.Lift.HatchGripper(true);
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
