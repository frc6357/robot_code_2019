package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *  Base class to be extended by any class requiring rollers
 */
public class BaseRoller
{
    // Speed Controller
    private SpeedController motorSpeed;
    private final int scalar;
    private int directionReader;

    /**
     *  Constructor:
     * 
     *  Creates the base class for any rollers
     * 
     *  @param motorSpeed
     *      - Type: Motor Controller
     *      - Used to control the speed of the motor
     * 
     */
    public BaseRoller(SpeedController motorSpeed)
    {
        this.motorSpeed = motorSpeed;
        scalar = 1;
    }
    /**
     *  Constructor:
     * 
     *  Creates the base class for any rollers
     * 
     *  @param motorSpeed
     *      - Type: Motor Controller
     *      - Used to control the speed of the motor
     * 
     *  @param scalar
     *      - Type: int
     *      - Used to set a slower speed when neccesary
     */
    public BaseRoller(SpeedController motorSpeed, int scalar)
    {
        // TODO: Write a set scalar method
        this.motorSpeed = motorSpeed;
        this.scalar = scalar;
    }
    /**
     *  Sets the roller motor/s to be spinning forwards
     */
    public void setForwards()
    {
        motorSpeed.set(1*scalar);
        directionReader = 1;
    }

    /**
     *  Sets the roller motor/s to be spinning backwards at a speed set by the scalar
     */
    public void setBackwards()
    {
        motorSpeed.set(-1*scalar);
        directionReader = -1;
    }
    /**
     *  Sets the roller motors to stop
     */
    public void setStop()
    {
        motorSpeed.set(0);
        directionReader = 0;
    }
    /**
     *  Returns the direction that the motor is set to
     *  @return 
     *      - Type int
     *      - Values of -1, 0, 1
     *      - Used to check whether motor is stopped, moving forwards or backwards
     */
    public int getDirection()
    {
        return directionReader;
    }
    // TODO: Create return scaler method
}