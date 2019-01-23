package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Base class for any proximity sensor
 */
public class BaseProximitySensor extends BaseLimitSensor
{
    private final DigitalInput input;

    public BaseProximitySensor(int port)
    {
        input = new DigitalInput(port);
    }

    @Override
    public boolean getIsTriggered()
    {
        return input.get();
    }
}