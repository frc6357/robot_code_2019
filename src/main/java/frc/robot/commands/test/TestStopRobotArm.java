/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.test;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;

public class TestStopRobotArm extends Command
{
    OI.Mode mode;

    public TestStopRobotArm(OI.Mode mode)
    {
        requires(Robot.armSystem);
        this.mode = mode;
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        if(Robot.oi.getMode() != mode)
            return;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        Robot.armSystem.stop();
        System.out.println("Stop EXECUTE");
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
