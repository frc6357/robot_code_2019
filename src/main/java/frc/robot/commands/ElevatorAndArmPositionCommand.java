package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the setting of the Elevators position
 */
public class ElevatorAndArmPositionCommand extends Command
{
    private OI.Mode mode;
    private String targetPosition;

    /**
     *
     * @param on If true, this command turns the roller motor on, if
     *           false, it turns the motor off.
     */
    public ElevatorAndArmPositionCommand(OI.Mode mode, String targetPosition)
    {
        requires(Robot.Lift);
        this.mode = mode;
        this.targetPosition = targetPosition;
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
        // Do not execute this command if we're not in the required mode!
        if(Robot.oi.getMode() != mode)
            return;
    
        // TODO: Code the ClimbStartWithCheck command.
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        Robot.Lift.setPositionTarget(targetPosition);
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
