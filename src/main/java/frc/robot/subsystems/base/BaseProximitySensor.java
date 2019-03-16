package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Base class for any proximity sensor
 */
public class BaseProximitySensor extends BaseLimitSensor
{
    // DIO
    private final DigitalInput input;

    // Polarity of triggered state
    private final boolean triggeredState;

    /**
     * Constructor
     *
     * @param port
     *      - Type: int
     *      - DIO port for proximity sensor
     */
    public BaseProximitySensor(int port)
    {
        input = new DigitalInput(port);

        triggeredState = true;
    }

    /**
     * Constructor
     *
     * @param port
     *      - Type: int
     *      - DIO port for proximity sensor
     *
     * @param triggeredState
     *      - Type: boolean
     *      - Set when the sensor is triggered
     */
    public BaseProximitySensor(int port, boolean triggeredState)
    {
        input = new DigitalInput(port);

        this.triggeredState = triggeredState;
    }

    /**
     * Gets if sensor is triggered
     */
    @Override
    public boolean getIsTriggered()
    {
        return input.get() ^ !triggeredState;
    }
}