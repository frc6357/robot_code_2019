/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class TestStopRobotArm extends Command 
{
    OI.Mode setMode;
    public TestStopRobotArm(OI.Mode setMode) 
    {
        System.out.println("Stop Constructor");
        //requires(Robot.Lift);
        this.setMode = setMode;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    
    protected void initialize() 
    {
        System.out.println("STOP");
        Robot.ArmMotorController.set(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    
    protected void execute() 
    {
        System.out.println("Stop EXECUTE");
    }

    // Make this return true when this Command no longer needs to run execute()
    
    protected boolean isFinished() 
    {
        return false;
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
