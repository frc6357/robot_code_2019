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
    private static enum states {INIT, WAIT, ARMWAIT, STARTMOTORS};
    private static states currentState;
    private static OI.Mode setMode;

    public CargoIntakeSystem(OI.Mode passedMode) 
    {
        requires(Robot.Intake);
        requires(Robot.Lift);
        setMode = passedMode;
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
                    currentState = states.ARMWAIT;
                }
                else
                {
                    currentState = states.STARTMOTORS;
                } 
            }
            break;
            case ARMWAIT:
            {
                if (Robot.Lift.armEncoder.getPosition() <= TuningParams.armCargoDeadband)
                {
                    currentState = states.STARTMOTORS;;
                }
                
            }
            break;
            case STARTMOTORS:
            {
                Robot.Intake.RollerArm.moveToAngleDegrees(TuningParams.intakeArmDeployedAngle);
                Robot.Intake.RollerMotor.set(TuningParams.intakeIngestMotorSpeed);
                Robot.Lift.OctopusRoller.setForwards();
                currentState = states.WAIT;
            }
            break;
            case WAIT:
            {
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
        Robot.Intake.RollerArm.moveToAngleDegrees(TuningParams.intakeArmStowedAngle);
        Robot.Intake.RollerMotor.set(0.0);
        Robot.Lift.OctopusRoller.setStop();
        currentState = states.INIT;
    }
}
