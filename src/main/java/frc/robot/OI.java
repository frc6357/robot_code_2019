// OI
//
// This is the operator interface class for the 2018 Team 6357 robot.
//

package frc.robot;

import frc.robot.commands.*;
import frc.robot.utils.FilteredJoystick;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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

    private FilteredJoystick joystickOperator;
    private FilteredJoystick joystickDriver;

    private Button buttonLowGear;
    private Button buttonHighGear;
    private Button buttonLeftClimb;

    public static Button buttonCameraShifter;
    // TODO: Add members for all operator functions (buttons, axes, etc)
    
    public OI()
    {
        // Instantiate the joystick devices.
        joystickOperator = new FilteredJoystick(Ports.OIOperatorJoystick);
        joystickDriver = new FilteredJoystick(Ports.OIDriverJoystick);

        buttonLowGear = new JoystickButton(joystickDriver, Ports.IODriverGearSelectLow);
        buttonHighGear = new JoystickButton(joystickDriver, Ports.IODriverGearSelectHigh);

        buttonLeftClimb = new JoystickButton(joystickOperator, Ports.OIOperatorJoystickLC);        

        buttonLowGear.whenPressed(new GearShiftCommand(false));
        buttonHighGear.whenPressed(new GearShiftCommand(true));
        buttonLeftClimb.whenPressed(new ClimbCommand(true));



        buttonCameraShifter = new JoystickButton(joystickDriver, Ports.OIDriverCameraSwitcher);
        // TODO: Add support for all additional operator and driver controls.
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
    public double getDriverJoystickValue(int port, boolean invert)
    {
        double multiplier = 1.0;

        if (invert)
        {
            multiplier = -1.0;
        }
        return multiplier * joystickDriver.getRawAxis(port);
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
        double multiplier = 1.0;

        if (invert)
        {
            multiplier = -1.0;
        }
        return (multiplier * joystickOperator.getRawAxis(port));
    }
}
