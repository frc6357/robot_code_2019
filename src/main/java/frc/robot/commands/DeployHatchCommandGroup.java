package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A class supporting the command sequence used to grab a hatch.
 */
public class DeployHatchCommandGroup extends CommandGroup
{
    OI.Mode mode;

    public DeployHatchCommandGroup(OI.Mode mode)
    {
        this.mode = mode;
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
        // Do not execute this command if we're not in the required mode!
        if(Robot.oi.getMode() != mode)
            return;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // TODO: Add group after testing
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
