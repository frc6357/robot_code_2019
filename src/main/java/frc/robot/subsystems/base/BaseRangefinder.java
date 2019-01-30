package frc.robot.subsystems.base;

/**
 * Base class for any rangefinder
 */
public abstract class BaseRangefinder
{
    public static final double OUT_OF_RANGE = -99999.0;
    public static final double NO_READING   = 0.0;
    private final double INCHES_PER_MM      = 0.0393701;

    /**
     * This function may be used to read the most recent rangefinder reading
     * in inches. If the rangefinder failed to measure a range last time it
     * was triggered, OUT_OF_RANGE is returned. If no reading has been taken,
     * NO_READING is returned.
     * 
     * @return Returns the distance in inches measured by the rangefinder the
     * last time a measurement completed. If no reading has been taken,
     * NO_READING is returned. If the last measurement failed to measure a
     * range, OUT_OF_RANGE is returned.
     */
    public double getDistanceInches()
    {
        double distance = getDistanceMm();
        if ((distance == OUT_OF_RANGE) || (distance == NO_READING))
        {
            return distance;
        }
        else
        {
            return (distance * INCHES_PER_MM);
        }
    }

    /**
     * This function may be used to read the rangefinder's minimum measurement
     * distance in inches.
     * 
     * @return Returns the minimum range supported by the rangefinder in inches.
     */
    public double getMinDistanceInches()
    {
        return (getMinDistanceMm() * INCHES_PER_MM);
    }

    /**
     * This function may be used to read the rangefinder's maximum measurement
     * distance in inches.
     * 
     * @return Returns the maximum range supported by the rangefinder in inches.
     */
    public double getMaxDistanceInches()
    {
        return (getMaxDistanceMm() * INCHES_PER_MM);
    }

    /**
     * This function may be used to read the rangefinder's maximum measurement
     * distance in millimeters.
     * 
     * @return Returns the maximum range supported by the rangefinder in millimeters.
     */
    public abstract double getMaxDistanceMm();

    /**
     * This function may be used to read the rangefinder's minimum measurement
     * distance in millimeters.
     * 
     * @return Returns the minimum range supported by the rangefinder in millimeters.
     */
    public abstract double getMinDistanceMm();

    /**
     * Tell the rangefinder to start the process of gathering a distance measurement.
     */
    public abstract void startMeasurement();

    /**
     * Query or not whether the last distance measurement is complete. This function
     * may be polled after calling startMeasurement() to determine when a new
     * distance reading is available.
     * 
     * @return Returns true if the last measurement has completed, false if measurement
     * is still ongoing.
     */
    public abstract boolean getIsMeasurementDone();

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
    public abstract double getDistanceMm();
}