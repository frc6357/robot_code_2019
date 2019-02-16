package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.utils.ScaledEncoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

//import frc.robot.subsystems.base.SPIEncoderAMT203V;

/**
 * Base class for an angled controlled arm
 *
 * THE PERIODIC FUNCTION MUST BE IMPLEMENTED
 */
public class BaseAngleControlledArm extends PIDSubsystem {

    private ScaledEncoder armEncoder;
    private BaseMotorizedArm arm;

    /**
     * Constructor:
     *
     * @param armRotateMotor - Type: SpeedController - Speed controller for
     *                                                 controlling arm motor
     *
     * @param armEncoder     - Type: SPIEncoderAMT203V - Encoder for tracking angle
     *
     * @param armSpeed       - Type: double - Constant speed for arm to move at
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
     * @param rampBand       - Type: double - The acceptable range for a motor change in one loop
     *
     */
    public BaseAngleControlledArm(BaseMotorizedArm arm, ScaledEncoder armEncoder, double kP,
                                    double kI, double kD, double tolerance) {

        super(kP, kI, kD);
        setAbsoluteTolerance(tolerance);

        this.armEncoder = armEncoder;
        this.arm = arm;

        armEncoder.reset();

        enable();
    }

    /**
     * Sets the set point angle to go to
     *
     * @param angle Type: double - angle for arm to go to
     */
    public void moveToAngle(double angle)
    {
        setSetpoint(angle);
    }

    public double getArmPosition()
    {
        return getPosition();
    }

    public double getArmSetpoint()
    {
        return getSetpoint();
    }

    @Override
    protected double returnPIDInput()
    {
        return armEncoder.getAngleDegrees();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        arm.setSpeed(output);
    }

    @Override
    protected void initDefaultCommand() {}
}