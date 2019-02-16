package frc.robot.commands;

import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive the intake arm to a given angle.
 */
public class IntakeArmPositionCommand extends Command
{   
    private OI.Mode mode;
    private double  setAngle;

    /**
     * 
     * @param mode - the operating mode in which this command must run.
     * @param angleDegrees - the angle that the arm will be moved to when this command runs.
     *    
     *   */
    public IntakeArmPositionCommand(OI.Mode mode, double angleDegrees)
    {
        requires(Robot.Intake);

        this.mode     = mode;
        this.setAngle = angleDegrees;
    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double speed = 0.0;
        
        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        // TODO: Code the IntakArmPositionCommand class
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
