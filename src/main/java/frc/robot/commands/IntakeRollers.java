package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the toggle of the Elevators position relative to the rocket
 */
public class IntakeRollers extends Command
{
    private boolean SelectOn;

    // TODO: Code the IntakeRollers command.
    
    /**
     * 
     * @param on determines whether or not to stop/start based on the value returned by toggle
     * @param toggle checks if the motor is on/off and changes the state its currently in based on its previous state. i.e (on to off/ off to on)
     *    
     *   */
    public IntakeRollers(boolean on, boolean toggle)
    {
        requires(Robot.Intake);
        SelectOn = on;

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
