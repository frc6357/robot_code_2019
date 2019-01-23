package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.DigitalInput;

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
        super(port);
    }

    /**
     * Get the state of the limit sensor
     *
     * @return
     *      - Type:  boolean
     *      - True:  is triggered
     *      - False: is NOT triggered
     */
    @Override
    public boolean getIsTriggered()
    {
        //TODO: Add specific "filter" for correct value to be read from Grove IR
        return this.getIsTriggered();
    }
}