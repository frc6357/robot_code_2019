package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.subsystems.base.BaseLimitSensor;
/**
 *  Base class that utilizes the BaseRoller class as well as the BaseLimitSensor class
 */
public class BaseOctopusRoller extends BaseRoller
{
    // Motor Controllers
    private final SpeedController motorController;
    // scaler Value
    private final static double scaler = 1;
    ///Limit Sensors
    private final BaseLimitSensor CargoSensor;
    

    /**
     *  Creates a base set of rollers for the octopus that utilizes a limit sensor
     *  @param CargoSensor
     *      - Type: BaseLimitSensor
     *      - Is the sensor that will detect whether the cargo is inside of the octopus
     *  @param motorController
     *      - Type: SpeedController
     *      - Passes the motor controller through that will be utilized by base roller
     *  @param scaler
     *      - Type: double
     *      - Sets a slower speed if neccesary to be able to slow the motors down
     */
    public BaseOctopusRoller(BaseLimitSensor CargoSensor, SpeedController motorController, double scaler)
    {
        
        super(motorController, scaler);
        this.motorController = motorController;
        this.CargoSensor = CargoSensor;
    }
    
    /**
     *  Checks whether the cargo sensor is activated
     *  @return
     *      - Type: boolean
     *      - Checks whether the sensor is activated and will return true
     */
    private boolean isCargoPressed()
    {
        return CargoSensor.getIsTriggered();
    }
    
}

