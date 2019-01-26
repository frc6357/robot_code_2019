package frc.robot.subsystems;

import frc.robot.subsystems.base.BaseTankDrive;

/**
 * The BaseTankDrive abstract class provides an interface suitable for 
 * implementing various types of tank drive. This is a low level class intended
 * merely to encapsulate the hardware implementation. It includes functions for
 * setting the basic motor speeds for each side of the robot, shifting gear
 * and reading encoders.
 */
public class SmoothDrive
{
    private BaseTankDrive Drive;

    private final int LEFT        = 0;
    private final int RIGHT       = 1;

    private double speedSet[]     = {0.0, 0.0};
    private double accelMax[]     = {1.0, 1.0};
    private double speedCurrent[] = {0.0, 0.0};
 
    /**
     * This constructor accepts a base drivetrain object and maximum acceleration values
     * for both the forward and backward directions. Accelerations are expressed in terms
     * of maximum motor speed delta between calls to the SmoothDrivePeriodic() function.
     * If this is called on every top level periodic callback, this represents the motor
     * speed change per 20 millisecond period.
     * 
     * As an example, assuming a 20 millisecond periodic interval, setting a max
     * acceleration value of 0.02 will allow the robot to go from stopped to maximum
     * speed in 1 second (initial speed 0.0, increased by 0.02 per 20mS interval
     * reaches 1.0 after 50 intervals).
     * 
     * @param Drive
     * @param accelMax
     * @param MaxBackAccel
     */
    public SmoothDrive(BaseTankDrive Drive, double accelMaxForward, double accelMaxBackward)
    {
        this.Drive           = Drive;
        this.accelMax[LEFT]  = accelMaxForward;
        this.accelMax[RIGHT] = accelMaxBackward;
    }

    public SmoothDrive(BaseTankDrive Drive, double accelMax)
    {
        this.Drive           = Drive;
        this.accelMax[LEFT]  = accelMax;
        this.accelMax[RIGHT] = accelMax;
    }

    /**
     * This method is used to send a double to the speed controller on the left side
     * of the robot.
     *
     * @param speed
     *            - speed is the double number between 1 and -1, usually from the
     *            joystick axis.
     */
    public void setLeftSpeed(double speed)
    {
        speedSet[LEFT] = speed;
    }

    /**
     * This method is used to send a double to the speed controller on the right
     * side of the robot.
     *
     * @param speed
     *            - speed is the double number between 1 and -1, usually from the
     *            joystick axis.
     */
    public void setRightSpeed(double speed)
    {
        speedSet[RIGHT] = speed;
    }

    /**
     * This method is used to change between low and high gear ratios.
     *
     * @param state
     *            - state is true to switch to high gear, false to switch to low
     *            gear.
     */
    public void setHighGear(boolean high)
    {
        Drive.setHighGear(high);
    }

    /**
     * This method is used to query the currently set gear.
     *
     * @return Return true if high gear is currently selected, false if low gear
     *         is selected.
     */
    public boolean getIsHighGear()
    {
        return Drive.getIsHighGear();
    }

    /**
     * This method is used query the raw reading from the left-side encoder.
     * Note that the class is also expected to implement a PIDSource 
     * interface allowing this to be wired a WPI PID controller.
     */
    public double getLeftEncoderRaw()
    {
        return Drive.getLeftEncoderRaw();
    }

    /**
     * This method is used query the raw reading from the right-side encoder.
     * Note that the class is also expected to implement a PIDSource 
     * interface allowing this to be wired a WPI PID controller.
     */
    public double getRightEncoderRaw()
    {
        return Drive.getRightEncoderRaw();
    }

    /**
     * This method is used to query the number of rotations the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the left encoder has recorded.
     */
    public double getLeftEncoderRotations()
    {
        return Drive.getLeftEncoderRotations();
    }

    /**
     * This method is used to query the number of rotations the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the right encoder has recorded.
     */
    public double getRightEncoderRotations()
    {
        return Drive.getRightEncoderRotations();
    }

    /**
     * This method is used to query the distance the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the left encoder has measured.
     */
    public double getLeftEncoderDistance()
    {
        return Drive.getLeftEncoderDistance();
    }

    /**
     * This method is used to query the distance the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the right encoder has measured.
     */
    public double getRightEncoderDistance()
    {
        return getRightEncoderDistance();
    }

    /**
     * This method must be called from the relevant top level "periodic" call.
     * It is responsible for updating the currend drivetrain motor speeds to
     * head towards the set value without exceeding the acceleration limits
     * set.
     */
    public void SmoothDrivePeriodic()
    {
        int loop;
        double delta;
        double speedNew[] = {0.0, 0.0};

        // Calculate left and right speeds in a loop.
        for(loop = 0; loop <= 1; loop++)
        {
            delta = (speedCurrent[loop] < speedSet[loop]) ? accelMax[loop] : -accelMax[loop];
            speedNew[loop] = speedCurrent[loop] + delta;

            // If the speed to be set has just crossed the set speed in the correct direction
            // then set the speed to the setpoint. 
            if (((delta > 0) && (speedNew[loop] > speedSet[loop])) ||
                ((delta < 0) && (speedNew[loop] < speedSet[loop])))
            {
                speedNew[loop] = speedSet[loop];
            }
        }

        // Now actually set the new speeds if they are different from the existing
        // speed.
        if(speedNew[LEFT] != speedCurrent[LEFT])
        {
            Drive.setLeftSpeed(speedNew[LEFT]);
            speedCurrent[LEFT] = speedNew[LEFT];
        }

        if(speedNew[RIGHT] != speedCurrent[RIGHT])
        {
            Drive.setRightSpeed(speedNew[RIGHT]);
            speedCurrent[RIGHT] = speedNew[RIGHT];
        }
    }
}
