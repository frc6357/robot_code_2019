/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.TuningParams;
import frc.robot.OI;

public class CargoIntakeSystem extends Command 
{
    private static enum states {INIT, WAIT, CAPTURED};
    private static states currentState;
    private static OI.Mode setMode;

    public CargoIntakeSystem(OI.Mode setMode) 
    {
        requires(Robot.Intake);
        requires(Robot.Lift);
        this.setMode = setMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        currentState = states.INIT;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        if (setMode != Robot.oi.getMode())
            return;
        switch (currentState)
        {
            case INIT:
            {
                if (Robot.Lift.armEncoder.getPosition() >= TuningParams.armCargoDeadband)
                {
                    Robot.Lift.RobotArmAngled.setSetpoint(0.0);
                    return;
                }
                Robot.Intake.RollerArm.moveToAngleDegrees(TuningParams.intakeArmDeployedAngle);
                Robot.Intake.RollerMotor.set(TuningParams.intakeIngestMotorSpeed);
                Robot.Lift.OctopusRoller.setForwards();
                currentState = states.WAIT;
            }
            break;
            case WAIT:
            {
                currentState = Robot.Lift.BallSensor.getIsTriggered() ? states.CAPTURED: states.WAIT;
                if (currentState == states.CAPTURED)
                {
                    setTimeout(TuningParams.cargoIntakeDownLimit);
                }
            }
            break;
            case CAPTURED:
            {
                if (isTimedOut())
                {
                    cleanUp();
                    currentState = states.INIT;
                }
            }
            break;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        boolean returnValue = false;
        if (currentState == states.INIT || Robot.oi.getMode() != setMode)
        {
            returnValue = true;
        }
        return returnValue;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
        cleanUp();
        currentState = states.INIT;
    }

    private void cleanUp()
    {
        Robot.Intake.RollerArm.moveToAngleDegrees(TuningParams.intakeArmStowedAngle);
        Robot.Intake.RollerMotor.set(0.0);
        Robot.Lift.OctopusRoller.setStop();
    }
}
