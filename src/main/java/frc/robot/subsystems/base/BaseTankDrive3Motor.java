package frc.robot.subsystems.base;

import frc.robot.Ports;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.utils.ScaledEncoder;

/**
 * The DriveBaseSystem subsystem controls all the basic functions of the speed
 * controllers for the drive train. It includes setting the speed for each side
 * of the robot, turning an angle, checking various states of the robot
 * including if the robot is stopped, and other states.
 *
 * The master speed controllers, "baseFrontLeftMaster" and
 * "baseFrontRightMaster" are followed by the other speed controllers. Follow
 * means that the speed sent to the master controllers are sent to the other
 * speed controllers on each side.
 *
 */
public class BaseTankDrive3Motor extends BaseTankDrive
{
    // Speed Controllers for the drive
    // The front controller is set to master, back is follower
    private final SpeedController baseFrontLeftMaster;
    private final SpeedController baseCenterLeft;
    private final SpeedController baseBackLeft;
    private final SpeedController baseFrontRightMaster;
    private final SpeedController baseCenterRight;
    private final SpeedController baseBackRight;

    // Gear shifter
    private final DoubleSolenoid baseGearShiftSolenoid;
    private boolean baseHighGear;

    // Encoders
    public final ScaledEncoder rightEncoder;
    public final ScaledEncoder leftEncoder;

    /**
     * The DriveBaseSystem constructor handles all the actuator object creation, and
     * sets the follow mode for the speed controllers
     */
    public BaseTankDrive3Motor()
    {
        super();

        // NB: We are using 3 motors through a gearbox on each side of the robot. In
        // this arrangement, the front and rear motors must run in one direction
        // and the matchign center motor must run in the opposite direction. Take
        // care to ensure that this is correct!

        // Left Drive Controllers
        baseFrontLeftMaster = new WPI_VictorSPX(Ports.driveLeftFrontMotor);
        baseCenterLeft = new WPI_VictorSPX(Ports.driveLeftCenterMotor);
        baseBackLeft = new WPI_VictorSPX(Ports.driveLeftRearMotor);

        // Right Drive Controllers
        baseFrontRightMaster = new WPI_VictorSPX(Ports.driveRightFrontMotor);
        baseCenterRight = new WPI_VictorSPX(Ports.driveRightCenterMotor);
        baseBackRight = new WPI_VictorSPX(Ports.driveRightRearMotor);

        // Inverts the speed controllers so they do not spin the wrong way. Remember that the 
        // center motor spins in the opposite direction from the front and back due to the 
        // gearbox we are using!
        baseBackRight.setInverted(true);
        baseCenterRight.setInverted(false);
        baseFrontRightMaster.setInverted(true);

        baseBackLeft.setInverted(false);
        baseCenterLeft.setInverted(true);
        baseFrontLeftMaster.setInverted(false);

        // Encoders
        leftEncoder  = new ScaledEncoder(Ports.driveLeftEncoderA,
                                         Ports.driveLeftEncoderB,
                                         Ports.driveEncoderPulsesPerRotation,
                                         Ports.driveWheelDiameterInches);
        rightEncoder = new ScaledEncoder(Ports.driveRightEncoderA,
                                         Ports.driveRightEncoderB,
                                         Ports.driveEncoderPulsesPerRotation,
                                         Ports.driveWheelDiameterInches);

        // This sets the all the speed controllers on the right side to follow the
        // center speed controller
        ((WPI_VictorSPX) baseCenterRight).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontLeftMaster).getDeviceID());
        ((WPI_VictorSPX) baseBackRight).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontRightMaster).getDeviceID());

        // This sets the all the speed controllers on the left side to follow the center
        // speed controller
        ((WPI_VictorSPX) baseCenterLeft).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontLeftMaster).getDeviceID());
        ((WPI_VictorSPX) baseBackLeft).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontLeftMaster).getDeviceID());

        // Gear shifter
        baseGearShiftSolenoid = new DoubleSolenoid(Ports.driveGearShiftPCM, Ports.driveGearShiftHigh, Ports.driveGearShiftLow);
        baseHighGear = false;

        // Sets Defaults
        leftEncoder.reset();
        rightEncoder.reset();
        setHighGear(baseHighGear);
    }

    /**
     * This method is used to reset both the left and right encoders on the drivetrain.
     */
    public void resetEncoders()
    {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    /**
     * This method is used to left side motor speed.
     *
     * @param speed
     *            - the speed to set. Valid values are in the range -1 to 1 where
     *              positive values indicate forwards and negative values indicate
     *              reverse.
     */
    public void setLeftSpeed(double speed)
    {
        baseFrontLeftMaster.set(speed);
    }

    /**
     * This method is used to right side motor speed.
     *
     * @param speed
     *            - the speed to set. Valid values are in the range -1 to 1 where
     *              positive values indicate forwards and negative values indicate
     *              reverse.
     */
    public void setRightSpeed(double speed)
    {
        baseFrontRightMaster.set(speed);
    }

    /**
     * This method is used to change between low and high gear ratios.
     *
     * @param state
     *            - true to switch to high gear, false to switch to low
     *            gear.
     */
    public void setHighGear(boolean high)
    {
        if (high)
        {
            baseGearShiftSolenoid.set(DoubleSolenoid.Value.kForward);
        } else
        {
            baseGearShiftSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
        baseHighGear = high;
    }

    /**
     * This method is used to query the currently set gear.
     *
     * @return Returns true if high gear is currently selected, false if low gear
     *         is selected.
     */
    public boolean getIsHighGear()
    {
        return baseHighGear;
    }

    /**
     * This method is used to query the raw reading from the left side encoder.
     *
     * @return Returns the current counter value of the left encoder. This is not scaled
     *         or otherwise converted to indicate rotations.
     */
    public double getLeftEncoderRaw() // Returns raw value of the encoder
    {
        return leftEncoder.getRaw();
    }

    /**
     * This method is used to query the raw reading from the right side encoder.
     *
     * @return Returns the current counter value of the right encoder. This is not scaled
     *         or otherwise converted to indicate rotations.
     */
    public double getRightEncoderRaw() // Returns raw value of the encoder
    {
        return rightEncoder.getRaw();
    }

    /**
     * This method is used to query the number of rotations the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the left encoder has recorded.
     */
    public double getLeftEncoderRotations() // Returns raw value of the encoder
    {
        double rotations = leftEncoder.getRotations();

        return rotations;
    }

    /**
     * This method is used to query the number of rotations the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the right encoder has recorded.
     */
    public double getRightEncoderRotations() // Returns raw value of the encoder
    {
        double rotations = rightEncoder.getRotations();

        return rotations;
    }

    /**
     * This method is used to query the distance the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the left encoder has measured.
     */
    public double getLeftEncoderDistance() // Returns raw value of the encoder
    {
        double rotations = leftEncoder.getRotations();

        return rotations;
    }

    /**
     * This method is used to query the distance the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the right encoder has measured.
     */
    public double getRightEncoderDistance() // Returns raw value of the encoder
    {
        double rotations = rightEncoder.getRotations();

        return rotations;
    }
}
