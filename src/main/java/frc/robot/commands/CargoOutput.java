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
import frc.robot.TuningParams;

public class CargoOutput extends Command 
{
    boolean firstStartUp = true;
    boolean finished;
    OI.Mode setMode;

    public CargoOutput(OI.Mode setMode) 
    {
        requires(Robot.Lift);
        this.setMode = setMode;
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
        if (setMode != Robot.oi.getMode())
            return;
        firstStartUp = true;
        finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
        if (setMode != Robot.oi.getMode())
            return;
        if (firstStartUp)
        {
            setTimeout(3.0);
            Robot.Lift.OctopusRoller.setForwards();
            firstStartUp = false;
        }
        
        if (isTimedOut())
        {
            Robot.Lift.OctopusRoller.setStop();
            firstStartUp = true;
            finished = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return finished;
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
