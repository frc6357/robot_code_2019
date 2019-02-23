package frc.robot.commands.test;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting toggling the state of the hatch deploy piston.
 */
public class TestToggleHatchDeploy extends Command
{
    private OI.Mode mode;

    public TestToggleHatchDeploy(OI.Mode mode)
    {
        requires(Robot.Lift);
        this.mode = mode;
    }
  
    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        boolean newState;

        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;
        
        newState = Robot.Lift.isHatchPusherExtended() ? false : true;
        Robot.Lift.HatchPusher(newState);
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
