package frc.robot.commands.test;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;

/**
 * A class supporting starting and stopping the intake mechanism arm position motor.
 */

public class TestIntakeMoveArm extends Command
{
<<<<<<< HEAD:src/main/java/frc/robot/commands/test/TestIntakeMoveArm.java
    private double newSpeed;
    private boolean moveOut;
    private boolean startMotor;
    private OI.Mode setMode;
=======
    private OI.Mode mode;
    private boolean startMotor;
    private boolean moveOut;

>>>>>>> develop:src/main/java/frc/robot/commands/TestIntakeMoveArm.java
    /**
     *
     * @param moveOut   Sets the direction in which the intake arm motor should move if startMotor is true.
     *              If the moveOut parameter is true, the arm moves towards the deployed position, if
     *              false, it moves towards the stowed position.
     * @param startMotor Determines whether the motor should be run or stopped. If true, the motor runs
     *              in the direction specified by the moveOutut parameter
     */
<<<<<<< HEAD:src/main/java/frc/robot/commands/test/TestIntakeMoveArm.java
    public TestIntakeMoveArm(OI.Mode setMode, boolean moveOut, boolean startMotor)
    {
        requires(Robot.Intake);
        this.newSpeed = 0.0;
        this.moveOut = moveOut;
        this.startMotor = startMotor;
        this.setMode = setMode;
=======
    public TestIntakeMoveArm(OI.Mode mode, boolean moveOut, boolean startMotor)
    {
        requires(Robot.Intake);
        this.mode       = mode;
        this.startMotor = startMotor;
        this.moveOut    = moveOut;
>>>>>>> develop:src/main/java/frc/robot/commands/TestIntakeMoveArm.java
    }


    // Called just before this Command runs the first time
    protected void initialize()
    {   
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
<<<<<<< HEAD:src/main/java/frc/robot/commands/test/TestIntakeMoveArm.java
        if (this.setMode == Robot.oi.getMode())
        {
            if(startMotor)
            {
                newSpeed = moveOut ? TuningParams.intakeArmMotorSpeed : -TuningParams.intakeArmMotorSpeed;
            }
            Robot.Intake.testSetArmMotorSpeed(newSpeed);
        }
=======
        double newSpeed = 0.0;

        // Only execute this if we're in the correct mode.
        if(mode != Robot.oi.getMode())
            return;

        if(startMotor)
        {
            newSpeed = moveOut ? Ports.intakeArmMotorSpeed : -Ports.intakeArmMotorSpeed;
        }

        Robot.Intake.testSetArmMotorSpeed(newSpeed);
>>>>>>> develop:src/main/java/frc/robot/commands/TestIntakeMoveArm.java
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
