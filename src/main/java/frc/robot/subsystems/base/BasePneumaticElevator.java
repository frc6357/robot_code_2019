package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *  Base class to be extended by any specific Pneumatic Elevator
 */
public class BasePneumaticElevator
{
    // Solenoid
    private final Solenoid elevatorSolenoid;

    // Limit Sensors
    private final BaseLimitSensor elevatorUpperLimit;
    private final BaseLimitSensor elevatorLowerLimit;

    /**
     *  Constructor:
     *
     *  Creates the base class for a pneumatic elevator
     *
     *  @param elevatorSolenoid
     *      - Type: Solenoid
     *      - Solenoid for height control
     *
     *  @param elevatorUpperLimit
     *      - Type: BaseLimitSensor
     *      - Can be any sensor that has BaseLimitSensor as the base class
     *
     *  @param elevatorLowerLimit
     *      - Type: BaseLimitSensor
     *      - Can be any sensor that has BaseLimitSensor as the base class
     */
    public BasePneumaticElevator(Solenoid elevatorSolenoid, BaseLimitSensor elevatorUpperLimit, BaseLimitSensor elevatorLowerLimit)
    {
        this.elevatorSolenoid = elevatorSolenoid;
        this.elevatorUpperLimit = elevatorUpperLimit;
        this.elevatorLowerLimit = elevatorLowerLimit;
    }

    /**
     *  Sets the state of the solenoid to be true / up / extend
     */
    public void setToUp()
    {
        elevatorSolenoid.set(true);
    }

    /**
     *  Sets the state of the solenoid to be false / down / retract
     */
    public void setToDown()
    {
        elevatorSolenoid.set(false);
    }

    public void setPosition(boolean bUp)
    {
        if (bUp == true && elevatorUpperLimit.getIsTriggered() != true)
        {
            elevatorSolenoid.set(false);
        }
        else if (bUp == false && elevatorLowerLimit.getIsTriggered () != true)
        {
            elevatorSolenoid.set(false);
        }
    }
    /**
     *  Gets the value from the lower limit sensor
     *
     *  @return
     *      - Type: Boolean
     *      - True: sensor is triggered, elevator is down
     *      - False: sensor is NOT triggered
     */
    public boolean getIsDown()
    {
        return elevatorLowerLimit.getIsTriggered();
    }

    /**
     *  Gets the value from the upper limit sensor
     *
     *  @return
     *      - Type: Boolean
     *      - True: sensor is triggered, elevator is up
     *      - False: sensor is NOT triggered
     */
    public boolean getIsUp()
    {
        return elevatorUpperLimit.getIsTriggered();
    }
}