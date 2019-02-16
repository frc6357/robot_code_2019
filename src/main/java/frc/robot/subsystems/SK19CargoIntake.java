package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Ports;
import frc.robot.subsystems.base.*;
import frc.robot.utils.ScaledEncoder;

/**
 * The SK19CargoIntake subsystem is responsible for all the hardware on the robot
 * related to picking up cargo from the floor and transfering it into the "octopus".
 *
 * TODO: The mechanism and detectors used to transfer the cargo from the back of the
 * robot, immediately after the rollers, to the octopus mechanism is not yet fully
 * defined. This should include a set of transfer rollers and a second proximity
 * detector to allow us to grab and hold the cargo until the octopus is in place to
 * receive it.
 */
public class SK19CargoIntake extends Subsystem
{
    public SpeedController        RollerMotor;
    public SpeedController        ArmMotor;
    public BaseAngleControlledArm RollerArm;
    public BaseProximitySensor    CargoPresentAtIntake;
    public BaseProximitySensor    CargoPresentAtTransfer;
    public SpeedController        TransferMotorLeft;
    public SpeedController        TransferMotorRight;
    public ScaledEncoder          ArmEncoder;

    double                 TransferMotorSpeed = 0.0;
    double                 RollerSpeed        = 0.0;

    /**
     * Constructor for the cargo intake mechanism.
     */
    public SK19CargoIntake()
    {
        // TODO: Which motor controllers are we using here? We assume TalonSRX for now.
        this.RollerMotor            = new WPI_TalonSRX(Ports.intakeRollerMotor);
        this.TransferMotorLeft      = new WPI_TalonSRX(Ports.intakeTransferMotorLeft);
        this.TransferMotorRight     = new WPI_TalonSRX(Ports.intakeTransferMotorRight);
        this.ArmMotor               = new WPI_TalonSRX(Ports.intakeArmMotor);

        // TODO: CHANGE VALUES TO NOT BE HARD CODED, TEST ONLY
        this.ArmEncoder             = new ScaledEncoder(9, 8, Ports.intakeEncoderPulsesPerRev, Ports.intakeArmEncoderDiameter);

        // TODO: Verify that the transfer motors do need to run in opposite directions.
        TransferMotorLeft.setInverted(false);
        TransferMotorRight.setInverted(true);

        // Set the right transfer motor to follow control input from the left.
        ((WPI_TalonSRX) TransferMotorRight).set(ControlMode.Follower, ((WPI_TalonSRX) TransferMotorLeft).getDeviceID());

        this.RollerArm              = new BaseAngleControlledArm(new BaseMotorizedArm(ArmMotor), this.ArmEncoder,
                                                                Ports.intakeArmPValue, Ports.intakeArmIValue,
                                                                Ports.intakeArmDValue, Ports.intakeArmToleranceValue);
        this.CargoPresentAtIntake   = new BaseProximitySensor(Ports.intakeIngestDetect, Ports.intakeIngestDetectState);
        this.CargoPresentAtTransfer = new BaseProximitySensor(Ports.intakeTransferDetect, Ports.intakeTransferDetectState);

        stopCargoIntake();
    }

    /**
    * Called periodically by the top level class, this method carries out any required
    * housekeeping operations. In this case, that means only passing the call on to the
    * rotating arm so that it can drive its position controller and handle the limit
    * switches.
    */
    public void periodic()
    {
    RollerArm.periodic();
    }

    /**
     * Immediately stop all motion in the cargo and transfer systems.
     */
    public void stopCargoIntake()
    {
        stopTransfer();
        RollerMotor.set(0.0);
        RollerSpeed = 0.0;
    }

    /**
     * Make the intake safe - stow any element that is deployed outside the robot frame
     * and turn off all motors.
     */
    public void safeCargoIntake()
    {
        stowCargoIntake();
        stopTransfer();
    }

    /**
     * Extend the arm and start spinning the intake rollers.
     */
    public void deployCargoIntake()
    {
        RollerArm.moveToAngleDegrees(Ports.intakeArmDeployedAngle);

        RollerSpeed = Ports.intakeIngestMotorSpeed;
        RollerMotor.set(RollerSpeed);
    }

    /**
     * Stop the intake rollers and pull the arm back within the robot perimeter.
     */
    public void stowCargoIntake()
    {
        RollerArm.moveToAngleDegrees(0.0);
        RollerSpeed = 0.0;
        RollerMotor.set(RollerSpeed);
    }

    /**
     * Determines whether there is a cargo piece within the robot right behind
     * the intake rollers.
     *
     * @return Returns true if a cargo piece is within the intake, false otherwise.
     */
    public boolean isCargoInIntake()
    {
        return CargoPresentAtIntake.getIsTriggered();
    }

    /**
     * Determines whether there is a cargo piece within the robot between the transfer
     * rollers.
     *
     * @return Returns true if a cargo piece is between the transfer rollers, false otherwise.
     */
    public boolean isCargoInTransfer()
    {
        return CargoPresentAtTransfer.getIsTriggered();
    }

    public void startTransfer(boolean bForward)
    {
        TransferMotorSpeed = bForward ? Ports.intakeTransferMotorSpeed : -Ports.intakeTransferMotorSpeed;
        TransferMotorLeft.set(TransferMotorSpeed);
    }

    public void stopTransfer()
    {
        TransferMotorSpeed = 0.0;
        TransferMotorLeft.set(TransferMotorSpeed);
    }


    /**
     * This function sets the speed of the roller motor. It must only be used in test mode.
     *
     * @param speed The speed of the roller motor.
     */
    public void testSetRollerSpeed(double speed)
    {
        RollerSpeed = speed;
        RollerMotor.set(speed);
    }

    /**
     * This function sets the speed of the arm motor. It must only be used in test mode.
     *
     * @param speed The speed of the arm motor.
     */
    public void testSetArmMotorSpeed(double speed)
    {
        ArmMotor.set(speed);
    }

    public double testGetRollerSpeed()
    {
        return RollerSpeed;
    }

    public void initDefaultCommand()
    {

    }
}