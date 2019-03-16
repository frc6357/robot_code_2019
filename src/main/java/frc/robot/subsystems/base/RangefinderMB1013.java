package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * This class supports the Maxbotix MB1013 ultrasonic rangefinder. The datasheet
 * for this device can be found at:
 * 
 * https://www.maxbotix.com/documents/HRLV-MaxSonar-EZ_Datasheet.pdf
 * 
 */
public class RangefinderMB1013 extends BaseRangefinder implements PIDSource
{
    private AnalogInput adc;
    private double MIN_MILLIVOLTS     = 293.0;
    private double MAX_MILLIVOLTS     = 4885.0;
    private double MAX_DISTANCE_MM    = 5000.0;
    private double MIN_DISTANCE_MM    = 300.0;
    private PIDSourceType pidSrc      = PIDSourceType.kDisplacement;

    public RangefinderMB1013(int adcChannel)
    {
        adc = new AnalogInput(adcChannel);
        MB1013Init();
    }

    public RangefinderMB1013(AnalogInput adc)
    {
        this.adc = adc;
        MB1013Init();
    }

    /**
     * This private initialization function is where any special ADC configuration
     * will be done should we need it. It is called from the class constructors.
     */
    private void MB1013Init()
    {
        // TODO: We may want to set the ADC sample rate or configure
        // oversampling/averaging here. For now, though, let's not bother.
    }

    /**
     * This function may be used to read the rangefinder's maximum measurement
     * distance in millimeters.
     * 
     * @return Returns the maximum range supported by the rangefinder in millimeters.
     */
    public double getMaxDistanceMm()
    {
        // Device spec indicates maximum measurement distance is 5m.
        return MAX_DISTANCE_MM;
    }

    /**
     * This function may be used to read the rangefinder's minimum measurement
     * distance in millimeters.
     * 
     * @return Returns the minimum range supported by the rangefinder in millimeters.
     */
    public double getMinDistanceMm()
    {
        // Device spec indicates minimum distance for reliable rangefinding is 30cm.
        return MIN_DISTANCE_MM;
    }

    /**
     * Tell the rangefinder to start the process of gathering a distance measurement.
     */
    public void startMeasurement()
    {
        // This device measures constantly so we don't need to trigger new
        // measurements.
    }

    /**
     * Query or not whether the last distance measurement is complete. This function
     * may be polled after calling startMeasurement() to determine when a new
     * distance reading is available.
     * 
     * @return Returns true if the last measurement has completed, false if measurement
     * is still ongoing.
     */
    public boolean getIsMeasurementDone()
    {
        // This device measures constantly so we don't need to trigger new
        // measurements. It's always ready to send a new reading.
        return true;
    }

    /**
     * This function may be used to read the most recent rangefinder reading
     * in millimeters. If the rangefinder failed to measure a range last time it
     * was triggered, OUT_OF_RANGE is returned. If no reading has been taken,
     * NO_READING is returned.
     * 
     * @return Returns the distance in millimeters measured by the rangefinder the
     * last time a measurement completed. If no reading has been taken,
     * NO_READING is returned. If the last measurement failed to measure a
     * range, OUT_OF_RANGE is returned.
     */
    public double getDistanceMm()
    {
        double mV;
        double range;

        // Read the ADC to which the rangefinder is attached.
        mV = 1000.0 * adc.getVoltage();

        // If the reading is out of range, tell the caller.
        if((mV < MIN_MILLIVOLTS) || (mV > MAX_MILLIVOLTS))
        {
            return BaseRangefinder.OUT_OF_RANGE;
        }

        // Convert the ADC reading to a range in millimeters.
        range = (mV * MAX_DISTANCE_MM) / MAX_MILLIVOLTS;

        return range;
    }

    /**
     * Test function which returns the raw ADC reading from the sensor.
     * 
     * @return The raw voltage read by the ADC. This is in volts rather than
     * millivolts.
     */
    public double getVoltage()
    {
        return adc.getVoltage();
    }
    /**
     * Methods implementing the PIDSource interface.
     * 
     * The setPIDSourceType functions seems odd given that we can only measure distance
     * but this implementation mirrors an example found on the web. We merely store the
     * source type passed and hand it back when asked. Really, we only support
     * PIDSourceType.kDisplacement, though.
     * 
     */
    public PIDSourceType getPIDSourceType()
    {
        return pidSrc;
    }

    public double pidGet()
    {
        return getDistanceMm();
    }

    public void setPIDSourceType(PIDSourceType type)
    {
        pidSrc = type;
    }
}