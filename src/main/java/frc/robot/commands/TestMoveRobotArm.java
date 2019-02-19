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

public class TestMoveRobotArm extends Command 
{
    private OI.Mode setMode;
    private boolean direction;
    public TestMoveRobotArm(OI.Mode Mode, boolean direction) 
    {  
        System.out.println("Move Constructor");
        //requires(Robot.Lift);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        //this.setMode = setMode;
        this.direction = direction;

    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        System.out.println("MOVE");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        System.out.println("EXECUTE");
        if(direction)
        {
            Robot.ArmMotorController.set(0.2);
             System.out.println("Should be Spinning Forwards");
        }
        else if(!direction)
        {
            Robot.ArmMotorController.set(-0.2);
            System.out.println("Should be spinning backwards");
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
