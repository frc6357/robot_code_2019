package frc.robot.subsystems.base;

/**
 * Base class for the Grove Infrared Reflective Sensor v1.2
 */
public class BaseGroveIRProximitySensor extends BaseProximitySensor
{
    /**
     * Constructor
     * Calls super with the port number
     *
     * @param port
     *      - Type: int
     *      - port of the Grove IR sensor
     */
    public BaseGroveIRProximitySensor(int port)
    {
        super(port, true);
    }
}