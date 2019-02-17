// OI
//
// This is the operator interface class for the 2018 Team 6357 robot.
//

package frc.robot;

import frc.robot.OI;
import frc.robot.commands.*;
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

    private static Button buttonCameraShifter;
    private static Button buttonShifter;
    private static Button buttonSlowMode;

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

    private static ModeSelect modeToggle = new ModeSelect();

    private static GearShiftCommand shiftLow = new GearShiftCommand(false);
    private static GearShiftCommand shiftHigh = new GearShiftCommand(true);

    public OI()
    {
        // Instantiate the driver joystick devices.
        joystickDriver = new FilteredJoystick(Ports.OIDriverJoystick);

        // Add an exponential filter to the driver joystick to damp the response around the zero
        // point. The coefficient here must be negative
        driveJoystickFilter = new ExponentialFilter(Ports.driveJoystickCoefficient);
        joystickDriver.setFilter(Ports.OIDriverLeftDrive, driveJoystickFilter);
        joystickDriver.setFilter(Ports.OIDriverRightDrive, driveJoystickFilter);

        buttonShifter = new JoystickButton(joystickDriver, Ports.IODriverGearShift);
        buttonSlowMode = new JoystickButton(joystickDriver, Ports.OIDriverSlow);

        buttonShifter.whenPressed(shiftLow);
        buttonShifter.whenReleased(shiftHigh);

        buttonSlowMode.whenPressed(new SlowModeCommand(true));
        buttonSlowMode.whenReleased(new SlowModeCommand(false));

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

        //*************************************
        // Button mappings common to all modes.
        //*************************************
        buttonOperatorBack.whenPressed(new ModeSelect());
        

        //************************************
        // Test mode operator button mappings.
        //************************************
        buttonOperatorA.whenPressed(new TestElevatorMove(OI.Mode.TEST, false));
        buttonOperatorY.whenPressed(new TestElevatorMove(OI.Mode.TEST, true));

        buttonOperatorB.whenActive(new TestIntakeMoveArm(OI.Mode.TEST, false, true));
        buttonOperatorB.whenInactive(new TestIntakeMoveArm(OI.Mode.TEST, false, false));
        buttonOperatorRightBumper.whenActive(new TestIntakeMoveArm(OI.Mode.TEST, true, true));
        buttonOperatorRightBumper.whenInactive(new TestIntakeMoveArm(OI.Mode.TEST, true, false));

        buttonOperatorX.whenPressed(new IntakeRollersCommand(OI.Mode.TEST, false, true));

        buttonOperatorLeftBumper.whenPressed(new TestToggleGrabHatch(OI.Mode.TEST));
        buttonOperatorStart.whenPressed(new TestToggleDeployHatch(OI.Mode.TEST));

        buttonOperatorLeftStick.whenPressed(new TestClimbStart(OI.Mode.TEST));
        buttonOperatorRightStick.whenPressed(new TestToggleTransportRoller(OI.Mode.TEST));

        buttonOperatorBack.whenPressed(new TestToggleClimbTilt(OI.Mode.TEST));

        //**************************************
        // Manual mode operator button mappings.
        //**************************************
        buttonOperatorA.whenPressed(new TestElevatorMove(OI.Mode.MANUAL, false));
        buttonOperatorY.whenPressed(new TestElevatorMove(OI.Mode.MANUAL, true));

        buttonOperatorB.whenActive(new TestIntakeMoveArm(OI.Mode.MANUAL, false, true));
        buttonOperatorB.whenInactive(new TestIntakeMoveArm(OI.Mode.MANUAL, false, false));
        buttonOperatorRightBumper.whenActive(new TestIntakeMoveArm(OI.Mode.MANUAL, true, true));
        buttonOperatorRightBumper.whenInactive(new TestIntakeMoveArm(OI.Mode.MANUAL, true, false));

        buttonOperatorX.whenPressed(new IntakeRollersCommand(OI.Mode.MANUAL, false, true));

        buttonOperatorLeftBumper.whenPressed(new GrabHatch(OI.Mode.MANUAL));
        buttonOperatorStart.whenPressed(new ReleaseHatch(OI.Mode.MANUAL));

        buttonOperatorLeftStick.whenPressed(new ClimbStartWithCheck(OI.Mode.MANUAL, buttonOperatorRightStick));
        buttonOperatorRightStick.whenPressed(new ClimbStartWithCheck(OI.Mode.MANUAL, buttonOperatorLeftStick));

        //**************************************
        // Normal mode operator button mappings.
        //**************************************
        // TODO: Set up control actions for normal mode.
        if(Robot.Lift.HatchSensor.getIsTriggered() || buttonOperatorLeftBumper.get())
            buttonOperatorLeftBumper.whenPressed(new GrabHatch(OI.Mode.NORMAL));

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
            }
            break;

            case NORMAL:
            {
                SmartDashboard.putString("Operator Mode", "NORMAL");
                SmartDashboard.putBoolean("Operator Override", false);
            }
            break;

            case MANUAL:
            {
                SmartDashboard.putString("Operator Mode", "MANUAL");
                SmartDashboard.putBoolean("Operator Override", true);
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
