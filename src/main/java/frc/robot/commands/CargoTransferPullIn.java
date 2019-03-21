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

public class CargoTransferPullIn extends Command 
{
    private static OI.Mode setMode;
    private static enum cargoModes {INIT, WAIT, CAPTURED};
    private static cargoModes currentMode = cargoModes.INIT;

    /**
     *  This is the second part of the cargo intake pull in code, where it is taken from the intake and then pulled into the octopus.
     *  @param setMode<br>
     *      - Type: OI.Mode<br>
     *      - The mode that this command should be running in
     */
    public CargoTransferPullIn(OI.Mode setMode) 
    {
        this.setMode = setMode;
        requires(Robot.Intake);
        requires(Robot.Lift);
    }

    /**
     *  When the command is called for the first time it checks if the mode that the command is running in is the proper mode, then it guarantees that the current mode is INIT
     */
    protected void initialize()
    {
        if (setMode != Robot.oi.getMode())
            return;
        currentMode = cargoModes.INIT;
    }

    /**
     *  Runs repeatedly until the command is told to stop, while running it checks the mode that it should be in, then runs that part of the code of the switch case statement.
     *  During INIT it boots everything up, the WAIT period it just checks if the cargo sensor is triggered, and if it is it sets a timeout, then in CAPTURED it waits for the timeout
     *  to expire and then calls cleanUp(), and then sets the mode to INIT.
     */
    protected void execute() 
    {
        if (setMode != Robot.oi.getMode())
            return;
        switch(currentMode)
        {
            case INIT:
            {
                Robot.Intake.RollerMotor.set(TuningParams.intakeRollerFullSpeed);
                Robot.Intake.RollerArm.setSetpoint(TuningParams.intakeArmStowedAngle);
                Robot.Lift.OctopusRoller.setForwards();
                Robot.Lift.transferRoller.setForwards();
                currentMode = cargoModes.WAIT;
            }
            break;
            case WAIT:
            {
                if (Robot.Lift.BallSensor.getIsTriggered())
                {
                    currentMode = cargoModes.CAPTURED;
                    setTimeout(TuningParams.cargoPullInWaitTime);
                }
            }
            break;
            case CAPTURED:
            {
                if (isTimedOut())
                {
                    cleanUp();
                    currentMode = cargoModes.INIT;
                }
            }
            break;
        }
    }

    /**
     *  This checks if the current mode is INIT, or if it is not running in the proper mode, then it returns true. Otherwise it returns false and keeps running.
     */
    protected boolean isFinished() 
    {
        return currentMode == cargoModes.INIT? true: false || Robot.oi.getMode() != setMode;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    }

    /**
     *  If another command requires the required subsytems, this method is called which sets the mode to INIT, then calls cleanUp()
     */
    protected void interrupted() 
    {
        currentMode = cargoModes.INIT;
        cleanUp();
    }

    /**
     *  This shuts down everything and be ready for arm movement.
     */
    public void cleanUp()
    {
        Robot.Lift.OctopusRoller.setStop();
        Robot.Intake.RollerMotor.set(TuningParams.intakeRollerStop);
        Robot.Lift.transferRoller.setStop();
    }
}
