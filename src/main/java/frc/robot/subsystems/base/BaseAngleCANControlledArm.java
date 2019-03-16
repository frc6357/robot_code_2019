package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import com.revrobotics.CANEncoder;

//import frc.robot.subsystems.base.SPIEncoderAMT203V;

/**
 * Base class for an angled controlled arm
 *
 * THE PERIODIC FUNCTION MUST BE IMPLEMENTED
 */
public class BaseAngleCANControlledArm extends PIDSubsystem {

    public CANEncoder armEncoder;
    private BaseMotorizedArm arm;
    private boolean invertMotor;

    /**
     * Constructor:
     *
     * @param armRotateMotor - Type: SpeedController - Speed controller for
     *                                                 controlling arm motor
     *
     * @param armEncoder     - Type: SPIEncoderAMT203V - Encoder for tracking angle
     *
     * @param kP             - Type: double - Porportional value of PID controller
     *
     * @param kI        `    - Type: double - Integral value of PID controller
     *
     * @param kD             - Type: double - Derivative value of PID controller
     *
     * @param tolerance      - Type: double - Set the percentage error which is considered
     *                                        tolerable for use with OnTarget.
     *
     */
    public BaseAngleCANControlledArm(BaseMotorizedArm arm, CANEncoder armEncoder, double kP,
                                    double kI, double kD, double tolerance, boolean invertMotor) {

        super(kP, kI, kD);
        setAbsoluteTolerance(tolerance);
        setOutputRange(-1.0, 1.0);
        setInputRange(0, 360);

        this.armEncoder = armEncoder;
        this.arm = arm;
        this.invertMotor = invertMotor;

        armEncoder.setPosition(0.0);
    }

    public void periodic()
    {
        arm.periodic();

        if (arm.getBottomLimitTriggered())
        {
            armEncoder.setPosition(0.0);
        }
    }

    /**
     * Sets the set point angle to go to
     *
     * @param angle Type: double - angle for arm to go to
     */
    public void moveToAngleDegrees(double angle)
    {
        if(angle >= 70)
        {
            setSetpoint(70);
        }
        else
        {
            setSetpoint(angle);
        }
    }

    public double getArmPosition()
    {
        // TODO: Maybe this should be read directly from the arm encoder, the interface is there to read directly
        return getPosition();
    }

    public double getArmSetpoint()
    {
        // TODO: May need to directly call returnPIDInput to be able to fully test and get the get method working
        return getSetpoint();
    }

    @Override
    protected double returnPIDInput()
    {
        double encoderAngle = armEncoder.getPosition();

        encoderAngle *= (1/54) * 90;
        return Math.max(encoderAngle, 0.0);
    }

    @Override
    protected void usePIDOutput(double output)
    {
        arm.setSpeed(invertMotor ? -output : output);
    }

    @Override
    protected void initDefaultCommand() {}
}