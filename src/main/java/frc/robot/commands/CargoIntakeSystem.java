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
     *  When called it extends the cargo systems and enables all of the rollers. It then checks the cargo sensors in the octopus system and when they're triggered it pulls
     *  the cargo intake system in and holds for one half second and stops the octopus rollers as well as the cargo rollers
     *  Will need to be tested as it may be completely wrong as we may never pull in a cargo if we don't pull up the intake with it in it. The intake system will have to guarantee
     *  which may require a new sensor to check if we have a piece of cargo in the cargo intake system. May be able to be a Grove IR sensor, but it may have to be one of the 12
     *  volt reflective IR sensors.
     *  TODO: Check on our out of bag day if we can get around needing a new sensor or if we need a new sensor to be able to reliably pull cargo in.
     *  TODO: Note at this point this code will only work in manual mode and will have to have the set mode modified before it would be able to work in normal mode.
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
                
            try 
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }).start();

        addParallel(new RunOctopusRollerCommand(OI.Mode.MANUAL, false));
        addParallel(new DeployIntakeCommand(OI.Mode.MANUAL, false));
        addParallel(new ToggleIntakeRollerCommand(OI.Mode.MANUAL));
    }
}
