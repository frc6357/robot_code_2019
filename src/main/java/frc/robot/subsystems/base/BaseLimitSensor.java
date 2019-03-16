package frc.robot.subsystems.base;

/**
 * Abstract class for any sensor that has a limit or only a true
 * or false, 0 or 1, state
 */
public abstract class BaseLimitSensor
{
    /**
     * Returns the state of the sensor
     *
     * @return
     *      - Type: boolean
     *      - True: Triggered
     *      - False: Is NOT triggered
     */
    public abstract boolean getIsTriggered();
}