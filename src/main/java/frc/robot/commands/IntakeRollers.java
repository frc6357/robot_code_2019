package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the toggle of the Elevators position relative to the rocket
 */
public class IntakeRollers extends Command
{
    private boolean SelectOn;

    /**
     * 
     * @param Speed sets the Rollers on deploy mecahnism to forward or back based on a boolean variable
     */
    public IntakeRollers(boolean On, boolean toggle)
    {
        requires(Robot.Intake);
        SelectOn = On;

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
