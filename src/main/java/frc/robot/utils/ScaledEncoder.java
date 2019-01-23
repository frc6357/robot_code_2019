package frc.robot.utils;

import edu.wpi.first.wpilibj.Encoder;

public class ScaledEncoder extends Encoder
{
    private int pulsesPerRotation;

    public ScaledEncoder(int channelA, int channelB, boolean reverseDirection, int pulses, double diameter)
    {
        super(channelA, channelB, reverseDirection);
        pulsesPerRotation = pulses;
        setDistancePerPulse((diameter * Math.PI) / pulses);
    }

    public ScaledEncoder(int channelA, int channelB, int pulses, double diameter)
    {
        super(channelA, channelB);
        pulsesPerRotation = pulses;
        setDistancePerPulse((diameter * Math.PI) / pulses);
    }

    public double getAngleDegrees()
    {
        return ((get() * 360) / pulsesPerRotation);
    }

    public double getAngleRadians()
    {
        return ((get() * 2 * Math.PI) / pulsesPerRotation);
    }

    public double getRotations()
    {
        return(get() / pulsesPerRotation);
    }
}