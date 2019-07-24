package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Servo;
import frc.robot.Ports;
import frc.robot.TuningParams;

public class SK19Climb extends Subsystem
{
    private final Solenoid climbLiftFront;
    private final Solenoid climbLiftBack;

    private final Servo climbWedgeLockServo;

    private final SpeedController climbWedgeWheelMotorLeft;
    private final SpeedController climbWedgeWheelMotorRight;

    public SK19Climb()
    {
        climbLiftFront = new Solenoid(Ports.climbPCM, Ports.climbLiftFront);
        climbLiftBack = new Solenoid(Ports.climbPCM, Ports.climbLiftBack);

        climbWedgeLockServo = new Servo(Ports.climbWedgeLockServo);

        climbWedgeWheelMotorLeft = new WPI_TalonSRX(Ports.climbWedgeWheelMotorLeft);
        climbWedgeWheelMotorRight = new WPI_TalonSRX(Ports.climbWedgeWheelMotorRight);

        lockWedge();
        retractBack();
        retractFront();
    }

    public void extend()
    {
        climbLiftFront.set(true);
        climbLiftBack.set(true);
    }

    public void retractFront()
    {
        climbLiftFront.set(false);
    }

    public void retractBack()
    {
        climbLiftBack.set(false);
    }

    public void unlockWedge()
    {
        climbWedgeLockServo.set(TuningParams.climbWedgeServoUnlockPosition);
    }

    public void lockWedge()
    {
        climbWedgeLockServo.set(TuningParams.climbWedgeServoLockPosition);
    }

    public void setWedgeWheelSpeed(double speed)
    {
        climbWedgeWheelMotorLeft.set(speed);
        climbWedgeWheelMotorRight.set(speed);
    }

    @Override
    protected void initDefaultCommand() {
    }
}

