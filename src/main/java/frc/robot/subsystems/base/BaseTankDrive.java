package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The BaseTankDrive abstract class provides an interface suitable for 
 * implementing various types of tank drive. This is a low level class intended
 * merely to encapsulate the hardware implementation. It includes functions for
 * setting the basic motor speeds for each side of the robot, shifting gear
 * and reading encoders.
 */
public abstract class BaseTankDrive extends Subsystem
{
    public PIDSource LeftPIDSource;
    public PIDSource RightPIDSource;

    /**
     * This method is used to send a double to the speed controller on the left side
     * of the robot.
     *
     * @param speed
     *            - speed is the double number between 1 and -1, usually from the
     *            joystick axis.
     */
    public abstract void setLeftSpeed(double speed);

    /**
     * This method is used to send a double to the speed controller on the right
     * side of the robot.
     *
     * @param speed
     *            - speed is the double number between 1 and -1, usually from the
     *            joystick axis.
     */
    public abstract void setRightSpeed(double speed);

    /**
     * This method is used to change between low and high gear ratios.
     *
     * @param state
     *            - state is true to switch to high gear, false to switch to low
     *            gear.
     */
    public abstract void setHighGear(boolean high);

    /**
     * This method is used to query the currently set gear.
     *
     * @return Return true if high gear is currently selected, false if low gear
     *         is selected.
     */
    public abstract boolean getIsHighGear();

    /**
     * This method is used query the raw reading from the left-side encoder.
     * Note that the class is also expected to implement a PIDSource 
     * interface allowing this to be wired a WPI PID controller.
     */
    public abstract double getLeftEncoderRaw();

    /**
     * This method is used query the raw reading from the right-side encoder.
     * Note that the class is also expected to implement a PIDSource 
     * interface allowing this to be wired a WPI PID controller.
     */
    public abstract double getRightEncoderRaw(); 

    /**
     * This method is used to query the number of rotations the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the left encoder has recorded.
     */
    public abstract double getLeftEncoderRotations();

    /**
     * This method is used to query the number of rotations the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of full rotations the right encoder has recorded.
     */
    public abstract double getRightEncoderRotations();

    /**
     * This method is used to query the distance the left encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the left encoder has measured.
     */
    public abstract double getLeftEncoderDistance();

    /**
     * This method is used to query the distance the right encoder has recorded
     * since the last time it was reset.
     *
     * @return Returns the number of inches the right encoder has measured.
     */
    public abstract double getRightEncoderDistance();

    /**
     * Each subsystem may, but is not required to, have a default command which is
     * scheduled whenever the subsystem is idle. If default command is needed use
     * "setDefaultCommand(new MyDefaultCommand());"
     */
    @Override
    protected void initDefaultCommand()
    {

    }
}
