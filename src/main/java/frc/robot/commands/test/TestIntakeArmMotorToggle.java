/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;

public class TestIntakeArmMotorToggle extends Command 
{
    private boolean startRollers = false;
    private boolean oldRollers;
    private OI.Mode setMode;
    public TestIntakeArmMotorToggle(OI.Mode setMode, boolean startRollers) 
    {
        this.startRollers = this.oldRollers;
        this.startRollers = startRollers;
        requires(Robot.Intake);
        if(this.startRollers == this.oldRollers)
        this.startRollers = false;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        if(this.setMode == Robot.oi.getMode())
        {
            double motorSpeed = startRollers ? 1.0 : 0.0;
            Robot.Intake.setRollerSpeed(motorSpeed);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
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
