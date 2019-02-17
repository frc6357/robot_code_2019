package frc.robot.commands;

import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.TuningParams;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the testing of the intake roller motor.
 */
public class IntakeRollersCommand extends Command
{   
    private boolean on;
    private boolean toggle;
    private OI.Mode mode;

    /**
     * 
     * @param on determines whether or not to stop/start based on the value returned by toggle
     * @param toggle checks if the motor is on/off and changes the state its currently in based on its previous state. i.e (on to off/ off to on)
     *    
     *   */
    public IntakeRollersCommand(OI.Mode mode, boolean on, boolean toggle)
    {
        requires(Robot.Intake);
        this.on     = on;
        this.toggle = toggle;
        this.mode   = mode;
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

        if(toggle == false)
        {
            speed = on ? TuningParams.intakeIngestMotorSpeed : 0.0;
        }
        else
        {
            double currentSpeed = Robot.Intake.testGetRollerSpeed();
            speed = (currentSpeed == 0.0) ? TuningParams.intakeIngestMotorSpeed : 0.0;
        }
        
        Robot.Intake.testSetRollerSpeed(speed);
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
