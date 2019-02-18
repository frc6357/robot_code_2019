/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SK19Lift;
import frc.robot.Ports;
import frc.robot.Robot;

public class TestMoveRobotArm extends Command 
{
    private double joystickValue;
    public TestMoveRobotArm(double joystickValue) 
    {
        requires(Robot.Lift);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.joystickValue = joystickValue;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        if(this.joystickValue != Robot.oi.getOperatorJoystickValue(Ports.OIOperatorJoystickTestARMPos, false))
        {
            Robot.Lift.testSetArmPositionMotorSpeed(Robot.oi.getOperatorJoystickValue(Ports.OIOperatorJoystickTestARMPos, false));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end()
    {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() 
    {
    }
}
