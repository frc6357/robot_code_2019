// OI
//
// This is the operator interface class for the 2018 Team 6357 robot.
//

package frc.robot;

import frc.robot.commands.*;
import frc.robot.utils.FilteredJoystick;

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

    private static Button buttonCameraShifter;
    private static Button buttonLowGear;
    private static Button buttonHighGear;

    private static FilteredJoystick joystickOperator;

    private static Button buttonOperatorA;
    private static Button buttonOperatorB;
    private static Button buttonOperatorX;
    private static Button buttonOperatorY;
    private static Button buttonOperatorLeftStick;
    private static Button buttonOperatorRightStick;
    private static Button buttonOperatorLeftBumper;
    private static Button buttonOperatorRightBumper;
    private static Button buttonOperatorBack;
    private static Button buttonOperatorStart;
    
    public enum Mode { NONE, TEST, NORMAL, MANUAL };

    private static Mode oiMode = Mode.NONE;

    public OI()
    {
        // Instantiate the driver joystick devices.
        joystickDriver = new FilteredJoystick(Ports.OIDriverJoystick);

        buttonLowGear = new JoystickButton(joystickDriver, Ports.IODriverGearSelectLow);
        buttonHighGear = new JoystickButton(joystickDriver, Ports.IODriverGearSelectHigh);

        buttonLowGear.whenPressed(new GearShiftCommand(false));
        buttonHighGear.whenPressed(new GearShiftCommand(true));

        // TODO: Revisit this if we end up having multiple cameras.
        buttonCameraShifter = new JoystickButton(joystickDriver, Ports.OIDriverCameraSwitcher);

        // Instantiate the operator joystick devices.
        joystickOperator = new FilteredJoystick(Ports.OIOperatorJoystick);

        buttonOperatorA           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonA);
        buttonOperatorB           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonB);
        buttonOperatorX           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonX);
        buttonOperatorY           = new JoystickButton(joystickOperator, Ports.OIOperatorButtonY);
        buttonOperatorLeftStick   = new JoystickButton(joystickOperator, Ports.OIOperatorJoystickL);
        buttonOperatorRightStick  = new JoystickButton(joystickOperator, Ports.OIOperatorJoystickR);
        buttonOperatorLeftBumper  = new JoystickButton(joystickOperator, Ports.OIOperatorLeftBumper);
        buttonOperatorRightBumper = new JoystickButton(joystickOperator, Ports.OIOperatorRightBumper);
        buttonOperatorBack        = new JoystickButton(joystickOperator, Ports.OIOperatorBack);
        buttonOperatorStart       = new JoystickButton(joystickOperator, Ports.OIOperatorStart);
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
            {
                SmartDashboard.putString("Operator Mode", "NONE");
                SmartDashboard.putBoolean("Operator Override", false);
            }
            break;

            case TEST:
            {
                SmartDashboard.putString("Operator Mode", "TEST");
                SmartDashboard.putBoolean("Operator Override", false);

                // TODO: Rework this to match the actual test mode control mapping
                //       when this is defined. For not, it's just a copy of the override
                //       mode mapping with the Back button action disabled.

                buttonOperatorA.whenPressed(new ElevatorMove(false));
                buttonOperatorY.whenPressed(new ElevatorMove(true));

                buttonOperatorB.whenActive(new IntakeMoveArm(false, true));
                buttonOperatorB.whenInactive(new IntakeMoveArm(false, false));
                buttonOperatorRightBumper.whenActive(new IntakeMoveArm(true, true));
                buttonOperatorRightBumper.whenInactive(new IntakeMoveArm(true, false));

                buttonOperatorX.whenPressed(new IntakeRollers(false, true));

                buttonOperatorLeftBumper.whenPressed(new TestToggleGrabHatch());
                buttonOperatorStart.whenPressed(new TestToggleDeployHatch());

                buttonOperatorLeftStick.whenPressed(new TestClimbStart());
                buttonOperatorRightStick.whenPressed(new TestToggleTransportRoller());

                buttonOperatorBack.whenPressed(new TestToggleClimbTilt());
            }
            break;

            case NORMAL:
            {
                SmartDashboard.putString("Operator Mode", "NORMAL");
                SmartDashboard.putBoolean("Operator Override", false);

                // TODO: Set up control actions for normal mode.
                buttonOperatorBack.whenPressed(new ModeSelect(true));
            }
            break;

            case MANUAL:
            {
                SmartDashboard.putString("Operator Mode", "MANUAL");
                SmartDashboard.putBoolean("Operator Override", true);

                buttonOperatorA.whenPressed(new ElevatorMove(false));
                buttonOperatorY.whenPressed(new ElevatorMove(true));

                buttonOperatorB.whenActive(new IntakeMoveArm(false, true));
                buttonOperatorB.whenInactive(new IntakeMoveArm(false, false));
                buttonOperatorRightBumper.whenActive(new IntakeMoveArm(true, true));
                buttonOperatorRightBumper.whenInactive(new IntakeMoveArm(true, false));

                buttonOperatorX.whenPressed(new IntakeRollers(false, true));

                buttonOperatorLeftBumper.whenPressed(new GrabHatch());
                buttonOperatorStart.whenPressed(new ReleaseHatch());

                buttonOperatorLeftStick.whenPressed(new ClimbStartWithCheck(buttonOperatorRightStick));
                buttonOperatorRightStick.whenPressed(new ClimbStartWithCheck(buttonOperatorLeftStick));

                buttonOperatorBack.whenPressed(new ModeSelect(false));
            }
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
