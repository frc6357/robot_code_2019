/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.util;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.Compressor;

public class CompressorToggle extends Command 
{
    boolean compressorState = true;
    Compressor robotCompressor;
    public CompressorToggle() 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        robotCompressor = new Compressor();
    }

    // Called just before this Command runs the first time
    
    protected void initialize() 
    {
    }

    // Called repeatedly when this Command is scheduled to run
    
    protected void execute() 
    {
        robotCompressor.setClosedLoopControl(!compressorState);
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
