package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.utils.ScaledEncoder;

/**
 *
 */
public class BaseAngledControlledArm extends BaseMotorizedArm {

    private final ScaledEncoder armEncoder;

    private final double STOP_SPEED_DEADBAND = 2.0;

    private double angleSetPoint;
    private double armSpeed;

    /**
     * Constructor:
     *
     * @param armRotateMotor - Type: SpeedController - Speed controller for
     *                       controlling arm motor
     *
     * @param armEncoder     - Type: Encoder - Encoder for tracking angle / speed /
     *                       distance
     *
     * @param armSpeed       - Type: double - Constant speed for arm to move at
     */
    public BaseAngledControlledArm(SpeedController armRotateMotor, ScaledEncoder armEncoder, double armSpeed) {
        super(armRotateMotor);

        this.armEncoder = armEncoder;
        this.armSpeed = armSpeed;

        armEncoder.reset();
        angleSetPoint = 0;
    }

    /**
     *  Constructor:
     *
     *  @param armRotateMotor
     *      - Type: SpeedController
     *      - Speed controller for controlling arm motor
     *
     *  @param armEncoder
     *      - Type: Encoder
     *      - Encoder for tracking angle / speed / distance
     *
     * @param armLimitTop
     *      - Type: int
     *      - DIO for upper limit sensor
     *
     * @param armLimitBottom
     *      - Type: int
     *      - DIO for bottom limit sensor
     */
    public BaseAngledControlledArm(SpeedController armRotateMotor, ScaledEncoder armEncoder, BaseLimitSensor armLimitTop, BaseLimitSensor armLimitBottom, double armSpeed)
    {
        super(armRotateMotor, armLimitBottom, armLimitTop);

        this.armEncoder = armEncoder;
        this.armSpeed = armSpeed;

        armEncoder.reset();
        angleSetPoint = 0;
    }

    /**
     *  Function to be called / placed in periodic
     */
    @Override
    public void periodic()
    {
        setStateFromSpeed();

        if (armEncoder.getAngleDegrees() < (angleSetPoint + STOP_SPEED_DEADBAND)
        && armEncoder.getAngleDegrees() > (angleSetPoint - STOP_SPEED_DEADBAND))
        {
            stop();
        }
        else if (armEncoder.getAngleDegrees() < angleSetPoint)
        {
            setSpeed(armSpeed);
        }
        else if (armEncoder.getAngleDegrees() > angleSetPoint)
        {
            setSpeed(armSpeed * -1);
        }

        if (getBottomLimitTriggered())
        {
            armEncoder.reset();
        }
    }

    /**
     * Sets the set point angle to go to
     *
     * @param angle Type: double - angle for arm to go to
     */
    public void moveToAngle(double angle)
    {
        angleSetPoint = angle;
    }
}