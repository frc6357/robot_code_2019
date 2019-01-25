package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subsystems.base.BaseLimitSensor;
/**
 * 
 */
//TODO: Needs to be finished and fully implemented
public class BaseOctopusRoller extends BaseRoller
{
    // Motor Controllers
    //private final SpeedController motorController;
    // scaler Value
    //private final static double scaler = 1;
    // Limit Sensors
    //private final BaseLimitSensor CargoSensor;

    public BaseOctopusRoller(BaseLimitSensor CargoSensor, SpeedController motorController, int scaler)
    {
        super(motorController, scaler);
        //this.motorController = motorController;
        //this.CargoSensor = CargoSensor;

    }
    
    /*private boolean isCargoPressed()
    {
        /**
         *  Takes the boolean value from CargoSensor and then calls the proper method if needed to stop or keep going
         *  Will need to check if it's set to forwards or backwards
         
        return CargoSensor.getIsTriggered();
    }
    */
}

