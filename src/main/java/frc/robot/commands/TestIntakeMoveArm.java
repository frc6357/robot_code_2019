package frc.robot.commands;

import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A class supporting starting and stopping the intake mechanism arm position motor.
 */

public class TestIntakeMoveArm extends Command
{
    private OI.Mode mode;
    private boolean startMotor;
    private boolean moveOut;

    /**
     * 
     * @param moveOut   Sets the direction in which the intake arm motor should move if startMotor is true.
     *              If the moveOut parameter is true, the arm moves towards the deployed position, if
     *              false, it moves towards the stowed position.
     * @param startMotor Determines whether the motor should be run or stopped. If true, the motor runs
     *              in the direction specified by the moveOutut parameter
     */
    public TestIntakeMoveArm(OI.Mode mode, boolean moveOut, boolean startMotor)
    {
        requires(Robot.Intake);
        this.mode       = mode;
        this.startMotor = startMotor;
        this.moveOut    = moveOut;
    }
  

    // Called just before this Command runs the first time
    protected void initialize()
    {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        double newSpeed = 0.0;

        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        if(startMotor)
        {
            newSpeed = moveOut ? Ports.intakeArmMotorSpeed : -Ports.intakeArmMotorSpeed;
        }

        Robot.Intake.testSetArmMotorSpeed(newSpeed);
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
