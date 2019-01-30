package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 */
public class BaseAngledControlledArm extends BaseMotorizedArm {

    private final Encoder armEncoder;

    /**
     * Constructor:
     *
     * @param armRotateMotor - Type: SpeedController - Speed controller for
     *                       controlling arm motor
     *
     * @param armEncoder     - Type: Encoder - Encoder for tracking angle / speed /
     *                       distance
     */
    public BaseAngledControlledArm(SpeedController armRotateMotor, Encoder armEncoder) {
        super(armRotateMotor);

        this.armEncoder = armEncoder;
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
    public BaseAngledControlledArm(SpeedController armRotateMotor, Encoder armEncoder, BaseLimitSensor armLimitTop, BaseLimitSensor armLimitBottom)
    {
        super(armRotateMotor, armLimitBottom, armLimitTop);

        this.armEncoder = armEncoder;
    }

    //TODO: Add functions
}