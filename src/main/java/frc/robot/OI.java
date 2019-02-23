// OI
//
// This is the operator interface class for the 2018 Team 6357 robot.
//

package frc.robot;

import frc.robot.commands.*;
import frc.robot.commands.test.*;
import frc.robot.commands.util.*;
import frc.robot.utils.FilteredJoystick;
import frc.robot.utils.filters.*;


import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups that allow control
 * of the robot.
 */
public class OI
{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by its isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    private static FilteredJoystick joystickDriver;
    private static ExponentialFilter driveJoystickFilter;

    private static Button buttonShifter;
    private static Button buttonSlowMode;

    private static FilteredJoystick joystickOperator;

    private static Button buttonOperatorA;
    private static Button buttonOperatorB;
    private static Button buttonOperatorX;
    private static Button buttonOperatorY;
    private static Button buttonOperatorLeftBumper;
    public static Button buttonOperatorRightBumper;
    private static Button buttonOperatorStart;

    // Currently unused...
    //private static Button buttonOperatorLeftStick;
    //private static Button buttonOperatorRightStick;
    //private static Button buttonOperatorBack;

    public static enum Mode { NONE, TEST, NORMAL, MANUAL };


    private static Mode oiMode = Mode.NONE;

    private static ModeSelect modeToggle = new ModeSelect();

    private static GearShiftCommand shiftLow = new GearShiftCommand(false);
    private static GearShiftCommand shiftHigh = new GearShiftCommand(true);

    //private static IntakeCancelCommand intakeCancel = new IntakeCancelCommand(OI.Mode.NORMAL);

    public OI()
    {
        // Instantiate the driver joystick devices.
        joystickDriver = new FilteredJoystick(Ports.OIDriverJoystick);

        // Add an exponential filter to the driver joystick to damp the response around the zero
        // point. The coefficient here must be negative
        driveJoystickFilter = new ExponentialFilter(TuningParams.driveJoystickCoefficient);
        joystickDriver.setFilter(Ports.OIDriverLeftDrive, driveJoystickFilter);
        joystickDriver.setFilter(Ports.OIDriverRightDrive, driveJoystickFilter);

        buttonShifter = new JoystickButton(joystickDriver, Ports.IODriverGearShift);
        buttonSlowMode = new JoystickButton(joystickDriver, Ports.OIDriverSlow);

        buttonShifter.whenPressed(shiftLow);
        buttonShifter.whenReleased(shiftHigh);

        buttonSlowMode.whenPressed(new SlowModeCommand(true));
        buttonSlowMode.whenReleased(new SlowModeCommand(false));

        // Instantiate the operator joystick devices.
        joystickOperator = new FilteredJoystick(Ports.OIOperatorJoystick);

        buttonOperatorA           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonA);
        buttonOperatorB           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonB);
        buttonOperatorX           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonX);
        buttonOperatorY           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonY);
        buttonOperatorLeftBumper  = new JoystickButton(joystickOperator, Ports.OIOperatorLeftBumper);
        buttonOperatorRightBumper = new JoystickButton(joystickOperator, Ports.OIOperatorRightBumper);
        buttonOperatorStart       = new JoystickButton(joystickOperator, Ports.OIOperatorStart);

        // Currently unused.
        //buttonOperatorBack        = new JoystickButton(joystickOperator, Ports.OIOperatorBack);
        //buttonOperatorLeftStick   = new JoystickButton(joystickOperator, Ports.OIOperatorJoystickL);
        //buttonOperatorRightStick  = new JoystickButton(joystickOperator, Ports.OIOperatorJoystickR);

        // Test mode command bindings.
        buttonOperatorRightBumper.whenPressed(new TestIntakeMoveArm(OI.Mode.TEST, true, true));
        buttonOperatorRightBumper.whenReleased(new TestIntakeMoveArm(OI.Mode.TEST, true, false));
        buttonOperatorX.whenPressed(new IntakeRollersCommand(OI.Mode.TEST, false, true));
        buttonOperatorB.whenPressed(new TestIntakeMoveArm(OI.Mode.TEST, false, true));
        buttonOperatorB.whenReleased(new TestIntakeMoveArm(OI.Mode.TEST, false, false));
        buttonOperatorA.whenPressed(new TestElevatorMove(OI.Mode.TEST, false));
        buttonOperatorY.whenPressed(new TestElevatorMove(OI.Mode.TEST, true));
        buttonOperatorLeftBumper.whenPressed(new TestGrabHatchToggle(OI.Mode.TEST));
        buttonOperatorStart.whenPressed(new TestToggleHatchDeploy(OI.Mode.TEST));

        // TODO: Right stick button, toggle transport rollers (when fitted)
        // TODO: Left stick Y axis, control lift arm speed.
        // TODO: Right trigger -ve, set octopus motor speed forwards.
        // TODO: Right stick Y axis, set climb motor speed.
        // TODO: Button 7, toggle climb tilt mechanism.

        // Manual mode command bindings.
        buttonOperatorA.whenPressed(new ElevatorPositionCommand(OI.Mode.MANUAL, false));
        buttonOperatorY.whenPressed(new ElevatorPositionCommand(OI.Mode.MANUAL, true));

        buttonOperatorB.whenPressed(new RunOctopusRollerCommand(OI.Mode.MANUAL, true));
        buttonOperatorB.whenReleased(new RunOctopusRollerCommand(OI.Mode.MANUAL, false));

        buttonOperatorX.whenPressed(new GrabHatchCommand(OI.Mode.MANUAL, true));
        buttonOperatorX.whenReleased(new GrabHatchCommand(OI.Mode.MANUAL, false));

        buttonOperatorRightBumper.whenPressed(new DeployIntakeCommand(OI.Mode.MANUAL, true));
        buttonOperatorRightBumper.whenReleased(new DeployIntakeCommand(OI.Mode.MANUAL, false));

        buttonOperatorLeftBumper.whenPressed(new ToggleIntakeRollerCommand(OI.Mode.MANUAL));

        buttonOperatorStart.whenPressed(new DeployHatchCommand(OI.Mode.MANUAL, true));
        buttonOperatorStart.whenReleased(new DeployHatchCommand(OI.Mode.MANUAL, false));

        // Nornmal mode command bindings
        buttonOperatorA.whenPressed(new ElevatorAndArmPositionCommand(OI.Mode.NORMAL, TuningParams.liftPositionLower));

        buttonOperatorB.whenPressed(new ElevatorAndArmPositionCommand(OI.Mode.NORMAL, TuningParams.liftPositionMiddle));

        buttonOperatorY.whenPressed(new ElevatorAndArmPositionCommand(OI.Mode.NORMAL, TuningParams.liftPositionUpper));

        buttonOperatorRightBumper.whenPressed(new ElevatorAndArmPositionCommand(OI.Mode.NORMAL, TuningParams.liftPositionStow));

        buttonOperatorX.whenPressed(new IntakeCommandGroup(OI.Mode.NORMAL));
    }

    /**
     * Set the transfer function coefficient used by the driver (speed) joystick.
     *
     * @param coeff - The coefficient to use. Valid values are -1.0 to 1.0.
     */
    public void setDriverJoystickCoefficient(double coeff)
    {
        driveJoystickFilter.setCoef(coeff);
    }

    /**
     *
     * This function may be called to retrieve the current, possibly-filtered, value of a given joystick axis on the driver joystick device.
     *
     * @param port
     *            The axis number for the joystick being queried. This will come from Ports.java. @ param invert If true, the returned
     *            joystick value will be inverted. If false, the unmodified joystick value is returned.
     * @return returnType The value of the joystick axis. Note that this may be a filtered value if we subclass the joystick to allow
     *         control of deadbands or response curves.
     */
    public double getDriverJoystickValue(int port)
    {
        return joystickDriver.getFilteredAxis(port);
    }

    /**
     *
     * This function may be called to retrieve the current, possibly-filtered, value of a given joystick axis on the operator joystick
     * device.
     *
     * @param port
     *            The axis number for the joystick being queried. This will come from Ports.java. @ param invert If true, the returned
     *            joystick value will be inverted. If false, the unmodified joystick value is returned.
     * @return returnType The value of the joystick axis. Note that this may be a filtered value if we subclass the joystick to allow
     *         control of deadbands or response curves.
     */
    public double getOperatorJoystickValue(int port, boolean invert)
    {
        double multiplier = 1;
        if (invert)
        {
            multiplier = -1.0;
        }
        //System.out.println(buttonOperatorLeftBumper.get() + " Left Bumper " + buttonOperatorRightBumper.get() + " Right Bumper ");
        return (multiplier * joystickOperator.getRawAxis(port));
    }

    /**
     * This method wires up the operator interface controls depending upon the mode that the
     * robot is operating in. Valid combinations of the two parameters are:
     *
     *                           ----------------------------------
     *                     bTest |    false         |     true    |
     * bManualOverride           |------------------|--------------
     *                    true   |     Manual       |     Test    |
     *                           | game operation   |     Mode    |
     *     --------------------------------------------------------
     *                    false  |     Normal       |     Test    |
     *                           | game operation   |     Mode    |
     *                           ----------------------------------
     *
     * @param bTest Sets operator controls for test mode.
     * @param bManualOverride Sets normal or manual override mode. Ignored if bTest is true.
     */
    public void setMode(boolean bTest, boolean bManualOverride)
    {
        // Test mode command bindings.
        if(bTest)
        {
            setMode(Mode.TEST);
        }
        else
        {
            // Manual override command bindings
            if(bManualOverride)
            {
                setMode(Mode.MANUAL);
            }
            else
            // Normal operation (teleop and "autonomous") command bindings
            {
                setMode(Mode.NORMAL);
            }
        }
    }

    /**
     * This method wires up the operator interface controls depending upon the mode that the
     * robot is to operate in.
     *
     * @param mode The desired operator mode - TEST, NORMAL or MANUAL. See the Robot User Manual for
     *             details of the control mappings in each mode.
     */
    public void setMode(Mode mode)
    {
        oiMode = mode;

        switch(mode)
        {
            case NONE:
                SmartDashboard.putString("Operator Mode", "NONE");
                SmartDashboard.putBoolean("Operator Override", false);
                break;

            case TEST:
                SmartDashboard.putString("Operator Mode", "TEST");
                SmartDashboard.putBoolean("Operator Override", false);
                break;

            case NORMAL:
                SmartDashboard.putString("Operator Mode", "NORMAL");
                SmartDashboard.putBoolean("Operator Override", false);
                break;

            case MANUAL:
                SmartDashboard.putString("Operator Mode", "MANUAL");
                SmartDashboard.putBoolean("Operator Override", true);
                break;
        }
    }

    /**
     * Query the current operating mode of the robot.
     *
     * @return The current mode, Mode.NONE, Mode.NORMAL, Mode.MANUAL or Mode.TEST
     */
    public Mode getMode()
    {
        return oiMode;
    }
}
