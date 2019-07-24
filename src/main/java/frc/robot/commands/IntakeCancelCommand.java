package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command group implementing the sequence necessary to pull cargo into the robot.
 */
public class IntakeCancelCommand extends Command
{
    private OI.Mode mode;

    /**
     *
     *
     **/
    public IntakeCancelCommand(OI.Mode mode)
    {
        requires(Robot.Intake);
        this.mode   = mode;
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
        if(mode != Robot.oi.getMode())
            return;

        Robot.Intake.stowCargoIntake();
        Robot.Intake.setRollerSpeed(0.0);
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
