package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SK19CargoIntake;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting starting and stopping the intake mechanism arm position motor.
 */

 // Must remember motor speed and only set it if its different
public class TestIntakeMoveArm extends Command
{
    /**
     * 
     * @param out   Sets the direction in which the intake arm motor should move if Start is true.
     *              If the Out parameter is true, the arm moves towards the deployed position, if
     *              false, it moves towards the stowed position.
     * @param start Determines whether the motor should be run or stopped. If true, the motor runs
     *              in the direction specified by the out parameter
     */
    public TestIntakeMoveArm(boolean out, boolean start)
    {
        requires(Robot.Intake);
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
