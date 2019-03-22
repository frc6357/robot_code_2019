/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;

public class ClimbBackPegsRetractCommand extends Command
{
    private OI.Mode setMode;
    private DriverStation driverStation;

    public ClimbBackPegsRetractCommand(OI.Mode setMode)
    {
        requires(Robot.Climb);
        this.setMode = setMode;
        this.driverStation = DriverStation.getInstance();
    }

    protected void initialize()
    {
        if (setMode != Robot.oi.getMode())
            return;
    }

    protected void execute()
    {
        if (driverStation.getMatchTime() <= 30.00)
        {
            Robot.Climb.retractBack();
        }
    }

    /**
     *  This checks if the current mode is INIT, or if it is not running in the proper mode, then it returns true. Otherwise it returns false and keeps running.
     */
    protected boolean isFinished()
    {
        return true;
    }

    // Called once after isFinished returns true
    protected void end()
    {
    }
}
