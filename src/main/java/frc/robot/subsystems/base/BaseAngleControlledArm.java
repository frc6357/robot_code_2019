package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.utils.ScaledEncoder;

//import frc.robot.subsystems.base.SPIEncoderAMT203V;

/**
 * Base class for an angled controlled arm
 *
 * THE PERIODIC FUNCTION MUST BE IMPLEMENTED
 */
public class BaseAngleControlledArm extends BaseMotorizedArm {

    private ScaledEncoder armEncoder;

    private final double STOP_ANGLE_DEADBAND = 2.0;

    private double angleSetPoint;
    private double armSpeed;

    //This constructor is only being used for testing purposes
    public BaseAngleControlledArm(SpeedController armRotateMotor, double armSpeed)
    {
        super(armRotateMotor);
    }
    /**
     * Constructor:
     *
     * @param armRotateMotor - Type: SpeedController - Speed controller for
     *                       controlling arm motor
     *
     * @param armEncoder     - Type: SPIEncoderAMT203V - Encoder for tracking angle 
     *
     * @param armSpeed       - Type: double - Constant speed for arm to move at
     */
    public BaseAngleControlledArm(SpeedController armRotateMotor, ScaledEncoder armEncoder, double armSpeed) {
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
    /*public BaseAngleControlledArm(SpeedController armRotateMotor, SPIEncoderAMT203V armEncoder, BaseLimitSensor armLimitTop, BaseLimitSensor armLimitBottom, double armSpeed)
    {
        super(armRotateMotor, armLimitBottom, armLimitTop);

        this.armEncoder = armEncoder;
        this.armSpeed = armSpeed;

        armEncoder.reset();
        angleSetPoint = 0;
    }
    */
    /**
     * THIS FUNCTION MUST BE ADDED TO A PERIODIC LOOP FOR ARM TO FUNCTION
     */
    /*
    public void periodic()
    {
        handleIsTriggered();

        if (armEncoder.getAngleDegrees() < (angleSetPoint + STOP_ANGLE_DEADBAND)
        && armEncoder.getAngleDegrees() > (angleSetPoint - STOP_ANGLE_DEADBAND))
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
    */
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