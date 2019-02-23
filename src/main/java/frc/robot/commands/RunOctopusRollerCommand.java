package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.TuningParams;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the setting of the Elevators position
 */
public class RunOctopusRollerCommand extends Command
{
    private OI.Mode mode;
    private boolean on;

    /**
     *
     * @param on If true, this command turns the roller motor on, if
     *           false, it turns the motor off.
     */
    public RunOctopusRollerCommand(OI.Mode mode, boolean on)
    {
        requires(Robot.Lift);
        this.mode = mode;
        this.on   = on;
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

        // Yes, this is a weird method. The speed parameter is positive
        // to move the motor forwards, zero to stop it, or negative to move
        // it backwards.
        Robot.Lift.setCargoRollerSpeed(on ? TuningParams.octopusMotorSpeed : 0.0);
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
