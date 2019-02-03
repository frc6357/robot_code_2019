package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.subsystems.base.BaseLimitSensor;
import frc.robot.subsystems.base.DummyLimitSensor;

/**
 * Base motorized arm class
 *
 * PERIODIC MUST BE ADDED TO A PERIODIC LOOP FOR ARM TO FUNCTION
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
     * Constructor
     *
     *  @param armRotateMotor
     *      - Type: SpeedController
     *      - Speed controller for controlling arm motor
     */
    public BaseMotorizedArm(SpeedController armRotateMotor)
    {
        armState = ArmStates.STOPPED;

        this.armRotateMotor = armRotateMotor;

        this.armLimitBottom = new DummyLimitSensor(false);
        this.armLimitTop = new DummyLimitSensor(false);
    }

    /**
     * THIS FUNCTION MUST BE ADDED TO A PERIODIC LOOP FOR ARM TO FUNCTION
     */
    public void periodic()
    {
        handleIsTriggered();
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
        if (speed > 0)
        {
            if (armLimitTop.getIsTriggered() && armState != ArmStates.STOPPED)
            {
                stop();
                armState = ArmStates.STOPPED;
            }
            else
            {
                armRotateMotor.set(speed);
                armState = ArmStates.MOVING_UP;
            }
        }
        else if (speed < 0)
        {
            if (armLimitBottom.getIsTriggered() && armState != ArmStates.STOPPED)
            {
                stop();
                armState = ArmStates.STOPPED;
            }
            else
            {
                armRotateMotor.set(speed);
                armState = ArmStates.MOVING_DOWN;
            }
        }
        else
        {
            stop();
        }
    }

    /**
     * Stops the arm when either limit is reached
     */
    public void handleIsTriggered()
    {
        if ((armLimitBottom.getIsTriggered() || armLimitTop.getIsTriggered())
            && armState != ArmStates.STOPPED)
        {
            stop();
            armState = ArmStates.STOPPED;
        }
    }

    /**
     *  Sets the speed of the armRotateMotor to 0
     */
    public void stop()
    {
        armRotateMotor.set(0);
    }

    /**
     * Gets the state of the bottom limit sensor
     *
     * @return Type: boolean - True: triggered, False: not triggered
     */
    public boolean getBottomLimitTriggered()
    {
        return armLimitBottom.getIsTriggered();
    }

    /**
     * Gets the state of the top limit sensor
     *
     * @return Type: boolean - True: triggered, False: not triggered
     */
    public boolean getTopLimitTriggered()
    {
        return armLimitTop.getIsTriggered();
    }
}