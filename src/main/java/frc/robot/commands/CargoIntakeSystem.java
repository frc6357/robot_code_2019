/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.RunOctopusRollerCommand;
import frc.robot.*;

public class CargoIntakeSystem extends CommandGroup 
{
     /**
     * Add your docs here.
     */
    public CargoIntakeSystem() 
    {
        
        requires(Robot.Lift);
        requires(Robot.Intake);
        
        addParallel(new RunOctopusRollerCommand(OI.Mode.MANUAL, true));
        addParallel(new DeployIntakeCommand(OI.Mode.MANUAL, true));
        addParallel(new ToggleIntakeRollerCommand(OI.Mode.MANUAL));
        
        while(!Robot.Lift.BallSensor.getIsTriggered())
        {}

        new Thread (() -> {
                
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
        addParallel(new RunOctopusRollerCommand(OI.Mode.MANUAL, false));
        addParallel(new DeployIntakeCommand(OI.Mode.MANUAL, false));
        addParallel(new ToggleIntakeRollerCommand(OI.Mode.MANUAL));
    }
}
