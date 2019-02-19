package frc.robot.commands.util;

import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class sets the normal or override operator control mode
 */
public class ModeSelect extends Command
{

    /**
     *
     * @param Override sets manual override mode if true, else sets normal control mode.
     */
    public ModeSelect()
    {

    }


    // Called just before this Command runs the first time
    protected void initialize()
    {
        OI.Mode RobotState = Robot.oi.getMode();
        if (RobotState == OI.Mode.NORMAL)
        {
            Robot.oi.setMode(OI.Mode.MANUAL);
        }
        else if(RobotState == OI.Mode.MANUAL)
        {
            Robot.oi.setMode(OI.Mode.NORMAL);
        }
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
