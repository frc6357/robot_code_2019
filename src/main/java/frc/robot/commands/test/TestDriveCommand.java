package frc.robot.commands.test;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the command sequence used to grab a hatch.
 */
public class TestDriveCommand extends Command
{
    OI.Mode mode;
    double speed;

    public TestDriveCommand(OI.Mode mode, double speed)
    {
        requires(Robot.BaseDrive);
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
        Robot.BaseDrive.setLeftSpeed(speed);
        Robot.BaseDrive.setRightSpeed(speed);
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
