package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.subsystems.base.BaseLimitSensor;

/**
 * Base motorized arm class
 */
public class BaseMotorizedArm
{
    // Rotate Speed Controller
    private final SpeedController armRotateMotor;

    // Limit Sensors
    private final BaseLimitSensor armLimitTop;
    private final BaseLimitSensor armLimitBottom;

    // States of the arm
    private enum ArmStates { MOVING_UP, MOVING_DOWN, STOPPED };

    // State accessor
    private ArmStates armState;


    /**
     *  Constructor:
     *
     *  @param armRotateMotor
     *      - Type: SpeedController
     *      - Speed controller for controlling arm motor
     *
     * @param armLimitTop
     *      - Type: int
     *      - DIO for upper limit sensor
     *
     * @param armLimitBottom
     *      - Type: int
     *      - DIO for bottom limit sensor
     */
    public BaseMotorizedArm(SpeedController armRotateMotor, BaseLimitSensor armLimitBottom, BaseLimitSensor armLimitTop)
    {
        armState = ArmStates.STOPPED;

        this.armRotateMotor = armRotateMotor;

        this.armLimitTop = armLimitTop;
        this.armLimitBottom = armLimitBottom;
    }

    /**
     *  Constructor:
     *
     *  @param armRotateMotor
     *      - Type: SpeedController
     *      - Speed controller for controlling arm motor
     *
     * @param armLimitTop
     *      - Type: int
     *      - DIO for upper limit sensor
     *
     */
    public BaseMotorizedArm(SpeedController armRotateMotor, BaseLimitSensor armLimitTop)
    {
        armState = ArmStates.STOPPED;

        this.armRotateMotor = armRotateMotor;

        this.armLimitTop = armLimitTop;
        this.armLimitBottom = new DummyLimitSensor(false);
    }

    /**
     *  Constructor:
     *
     *  @param armRotateMotor
     *      - Type: SpeedController
     *      - Speed controller for controlling arm motor
     *
     * @param armLimitBottom
     *      - Type: int
     *      - DIO for bottom limit sensor
     *
     */
    public BaseMotorizedArm(SpeedController armRotateMotor, BaseLimitSensor armLimitBottom)
    {
        armState = ArmStates.STOPPED;

        this.armRotateMotor = armRotateMotor;

        this.armLimitBottom = armLimitBottom;
        this.armLimitTop = new DummyLimitSensor(false);
    }

    public BaseMotorizedArm(SpeedController armRotateMotor)
    {
        armState = ArmStates.STOPPED;

        this.armRotateMotor = armRotateMotor;

        this.armLimitBottom = new DummyLimitSensor(false);
        this.armLimitTop = new DummyLimitSensor(false);
    }

    /**
     *  Function to be called / placed in periodic
     */
    public void periodic()
    {
        setStateFromSpeed();
    }

    /**
     * Sets ArmStates armState based on the value passed to the speed controller
     */
    public void setStateFromSpeed()
    {
        if (armRotateMotor.get() > 0)
        {
            armState = ArmStates.MOVING_UP;
        }

        if (armRotateMotor.get() < 0)
        {
            armState = ArmStates.MOVING_DOWN;
        }

        if (armRotateMotor.get() == 0)
        {
            armState = ArmStates.STOPPED;
        }
    }

    /**
     *  Set the speed of the arm
     *
     * @param speed
     *      - Type: double
     *      - Speed from a range of -1 : 1
     */
    public void setSpeed(double speed)
    {
        if (armState == ArmStates.MOVING_UP)
        {
            if (armLimitTop.getIsTriggered())
            {
                stop();
            }
            else
            {
                armRotateMotor.set(speed);
            }
        }

        if (armState == ArmStates.MOVING_DOWN)
        {
            if (armLimitBottom.getIsTriggered())
            {
                stop();
            }
            else
            {
                armRotateMotor.set(speed);
            }        }
    }

    /**
     *  Sets the speed of the armRotateMotor to 0
     */
    public void stop()
    {
        armRotateMotor.set(0);
    }
}