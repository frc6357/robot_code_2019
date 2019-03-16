package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *  Base class to be extended by any class requiring rollers
 */
public class BaseRoller
{
    // Speed Controller
    private SpeedController motorController;
    private double speed = 1.0;
    private double currentSpeed = 0.0;
    public static enum Direction { BACKWARD, STOPPED, FORWARD };
    private Direction directionReader;

    /**
     *  Constructor:
     * 
     *  Creates the base class for any rollers
     * 
     *  @param motorController
     *      - Type: Motor Controller
     *      - Used to control the speed of the motor
     * 
     */
    public BaseRoller(SpeedController motorController)
    {
        this.motorController = motorController;
        setSpeed(0);
    }

    /**
     *  Constructor:
     * 
     *  Creates the base class for any rollers
     * 
    //  *  @param motorController
     *      - Type: Motor Controller
     *      - Used to control the speed of the motor
     * 
     *  @param speed
     *      - Type: double
     *      - Used to set a slower speed when neccesary
     */
    public BaseRoller(SpeedController motorController, double speed)
    {
        this.motorController = motorController;
        this.speed = speed;
    }

    /**
     *  Sets the roller motor/s to be spinning forwards
     */
    public void setForwards()
    {
        if(currentSpeed != speed)
        {
            currentSpeed = speed;
            motorController.set(currentSpeed);
        }
        directionReader = Direction.FORWARD;
    }

    /**
     *  Sets the roller motor/s to be spinning backwards at a speed set by the speed
     */
    public void setBackwards()
    {
        if(currentSpeed != -speed)
        {
            currentSpeed = -speed;
            motorController.set(currentSpeed);
        }
        directionReader = Direction.BACKWARD;
    }

    /**
     *  Sets the roller motors to stop
     */
    public void setStop()
    {
        if(currentSpeed != 0.0)
        {
            currentSpeed = 0.0;
            motorController.set(0.0);
        }
        directionReader = Direction.STOPPED;
    }

    /**
     *  Returns the direction that the motor is set to
     *  @return 
     *      - Type int
     *      - Values of -1, 0, 1
     *      - Used to check whether motor is stopped, moving forwards or backwards
     */
    public BaseRoller.Direction getDirection()
    {
        return directionReader;
    }

    /**
     *  Sets or changes the speed that is used by the motor controller
     *  @param newSpeed
     *      - Type double
     *      - Changes the speed of the rollers
     */
    public void setSpeed(double newSpeed)
    {
        this.speed = newSpeed;
    }

    /**
     *  Returns the speed that is set to the default motherboard
     *  @return
     *      - Type double
     *      - Current speed that the motor controller is utilizing
     */
    public double returnSpeed()
    {
        return this.speed;
    }

    /**
     * Function for test use only. This sets the motor to run at the commanded speed,
     * regardless of the speed set during initialization.
     */
    public void testSetSpeed(double speed)
    {
        motorController.set(speed);
        currentSpeed = speed;
        directionReader = (speed > 0.0) ? Direction.FORWARD : ((speed == 0.0) ? Direction.STOPPED : Direction.BACKWARD);
    }

    /**
     * Retrieve the current speed of the roller.
     */
    public double testGetSpeed()
    {
        return currentSpeed;
    }
}