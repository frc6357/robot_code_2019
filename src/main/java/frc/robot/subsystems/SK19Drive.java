package frc.robot.subsystems;

import frc.robot.Ports;
import frc.robot.TuningParams;
import frc.robot.subsystems.base.BaseTankDrive2Motor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.utils.ScaledEncoder;

/**
 *  The SK19Lift subsystem is responsible for both the elevator and arm systems that
 *  are responsible for moving the cargo and hatch to a set series of points.
 */
public class SK19Drive extends Subsystem
{
    private final SpeedController baseFrontLeftMaster;
    private final SpeedController baseBackLeft;
    private final SpeedController baseFrontRightMaster;
    private final SpeedController baseBackRight;

    // Gear shifter
    private final Solenoid baseGearShiftSolenoid;

    // Encoders
    public final ScaledEncoder      rightEncoder;
    public final ScaledEncoder      leftEncoder;

    public final SmoothDrive         Drive;
    public final BaseTankDrive2Motor baseDrive;

    public SK19Drive()
    {

        // NB: We are using 2 motors through a gearbox on each side of the robot.

        // Left Drive Controllers
        baseFrontLeftMaster = new WPI_VictorSPX(Ports.driveLeftFrontMotor);
        baseBackLeft = new WPI_VictorSPX(Ports.driveLeftRearMotor);

        // Right Drive Controllers
        baseFrontRightMaster = new WPI_VictorSPX(Ports.driveRightFrontMotor);
        baseBackRight = new WPI_VictorSPX(Ports.driveRightRearMotor);

        // Inverts the speed controllers so they do not spin the wrong way.
        baseBackRight.setInverted(true);
        baseFrontRightMaster.setInverted(true);

        baseBackLeft.setInverted(false);
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
        ((WPI_VictorSPX) baseBackRight).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontRightMaster).getDeviceID());

        // This sets the all the speed controllers on the left side to follow the center
        // speed controller
        ((WPI_VictorSPX) baseBackLeft).set(ControlMode.Follower, ((WPI_VictorSPX) baseFrontLeftMaster).getDeviceID());

        // Gear shifter
        baseGearShiftSolenoid = new Solenoid(Ports.driveGearShiftPCM, Ports.driveGearShiftHigh);

        this.baseDrive = new BaseTankDrive2Motor(this.baseFrontLeftMaster, this.baseBackLeft, this.baseFrontRightMaster,
                                                        this.baseBackRight, this.baseGearShiftSolenoid, this.rightEncoder, this.leftEncoder);

        this.Drive = new SmoothDrive(baseDrive, TuningParams.driveMaxAccelForward, TuningParams.driveMaxAccelBackwards);
    }

    @Override
    protected void initDefaultCommand() {

    }
}