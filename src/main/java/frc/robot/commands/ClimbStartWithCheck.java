package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A class supporting the climb command. For safety, this class requires 2 buttons to
 * be pressed before the command will actually activate climb. The constructor takes a
 * button object which must be checked in the constructor. If this second button is
 * pressed then the climb command is activated. If not, we ignore the command and 
 * report finished immediately.
 */
public class ClimbStartWithCheck extends Command
{
    /**
     * 
     * @param Override sets manual override mode if true, else sets normal control mode.
     */
    public ClimbStartWithCheck(Button CheckBtn)
    {
        // TODO: Code the ClimbStartWithCheck command.
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
