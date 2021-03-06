package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the command sequence used to grab a hatch.
 */
public class DeployIntakeCommand extends Command
{
    private OI.Mode mode;
    private boolean deploy;

    public DeployIntakeCommand(OI.Mode mode, boolean deploy)
    {
        requires(Robot.Intake);

        this.mode   = mode;
        this.deploy = deploy;
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

        if(deploy)
        {
            Robot.Intake.deployCargoIntake();
        }
        else
        {
            Robot.Intake.stowCargoIntake();
        }
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
