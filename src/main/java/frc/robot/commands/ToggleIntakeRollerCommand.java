package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.TuningParams;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting toggling the intake roller motor on and off.
 */
public class ToggleIntakeRollerCommand extends Command
{
    OI.Mode mode;

    public ToggleIntakeRollerCommand(OI.Mode mode)
    {
        requires(Robot.Intake);
        this.mode = mode;
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

        if(Robot.Intake.getRollerSpeed() == 0)
        {
            Robot.Intake.setRollerSpeed(TuningParams.intakeIngestMotorSpeed);
        }
        else
        {
            Robot.Intake.setRollerSpeed(0.0);
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
