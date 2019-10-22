/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;

public class IntakePullThrough extends Command 
{
    private static OI.Mode setMode;
    private static boolean forwardsActive;
    public IntakePullThrough(OI.Mode passedMode, boolean isForwards) 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.Lift);
        setMode = passedMode;
        forwardsActive = isForwards;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        if(setMode != Robot.oi.getMode())
            return;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        if(forwardsActive)
        {
            Robot.Lift.octopusMotor.set(1.0);
        }
        else
        {
            Robot.Lift.octopusMotor.set(0.0);
        }
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
