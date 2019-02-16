package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting the setting of the Elevators position
 */
public class TestElevatorMove extends Command
{   
    /**
     * 
     * @param Up sets the Elevator to up or down based on a boolean variable
     */
    public TestElevatorMove(boolean up)
    {
        requires(Robot.Lift);
    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        Robot.Lift.testSetElevatorPosition(up);
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
