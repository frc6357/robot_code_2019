package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the toggle of the Elevators position relative to the rocket
 */

 // Must remember motor speed and only set it if its different
public class IntakeMoveArm extends Command
{
    private boolean SelectOut;
    
    // TODO: Code the IntakeMoveArm command.
    
    /**
     * 
     * @param Speed sets the Rollers on deploy mecahnism to forward or back based on a boolean variable
     */
    public IntakeMoveArm(boolean Out, boolean Start)
    {
        requires(Robot.Intake);
        SelectOut = Out;

    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
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
