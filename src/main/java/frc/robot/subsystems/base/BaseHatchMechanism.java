package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.subsystems.base.BaseLimitSensor;

/**
 *  Base class that deploys the hatch and extends the hatch mechanism
 */
public class BaseHatchMechanism
{
    // Solenoids
    private final DoubleSolenoid hatchDeploySolenoid;
    private final DoubleSolenoid hatchLatchSolenoid;

    // Limit Sensors
    private final BaseLimitSensor hatchDetector;

    /**
     *  Intializes the required solenoids and sensors
     *  @param hatchDeploySolenoid
     *     - Type: Solenoid
     *     - Controls the slider mechanism that moves the hatch to the rocket when retracted.
     *       This object must be created such that energizing the forward channel pushes the
     *       hatch off the gripper and energizing the reverse channel retracts the piston.
     *  @param hatchLatchSolenoid
     *     - Type: Solenoid
     *     - Controls the hatch mechanism to either open or close the mechanism. The forward channel
     *       opens the gripper and the reverse channel closes it.
     *  @param hatchDetector
     *     - Type: BaseLimitSensor
     *     - Detects whether the hatch is on the mechanism or not
     */
    public BaseHatchMechanism(DoubleSolenoid hatchDeploySolenoid, DoubleSolenoid hatchLatchSolenoid, BaseLimitSensor hatchDetector)
    {
        this.hatchDeploySolenoid = hatchDeploySolenoid;
        this.hatchLatchSolenoid  = hatchLatchSolenoid;
        this.hatchDetector       = hatchDetector;

        hatchDisable();
    }

    /**
     *  Push the hatch off the mechanism using the piston.
     */
    public void extendHatchPiston()
    {
        hatchDeploySolenoid.set(DoubleSolenoid.Value.kForward);
    }

    /**
     *  Retracts piston pushing the hatch to the rocket
     */
    public void retractHatchPiston()
    {
        hatchDeploySolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Query the current state of the hatch deploy piston. Returns True if extended, false otherwise.
     */
    public boolean isHatchPistonExtended()
    {
        return ((hatchDeploySolenoid.get() == DoubleSolenoid.Value.kForward) ? true : false);
    }

    /**
     *  Opens the latch gripper to lock the hatch onto the mechanism.
     */
    public void latchHatch()
    {
        hatchLatchSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    /**
     *  Close the hatch gripper to allow the hatch to be expelled.
     */
    public void unlatchHatch()
    {
        hatchLatchSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Query the current state of the hatch latch. Returns True if locked, false otherwise.
     */
    public boolean isHatchLatched()
    {
        return ((hatchLatchSolenoid.get() == DoubleSolenoid.Value.kForward) ? true : false);
    }

    /**
     *  Detects whether there is a hatch on the mechanism
     *  @return
     *      - Type: boolean
     *      - Will be true if hatch is on sensor, and false if no hatch is on the sensor
     */
    public boolean hatchIsPresent()
    {
        return hatchDetector.getIsTriggered();
    }

    /**
     * Disables (turns off) both channels of both solenoids used in the mechanism.
     */
    public void hatchDisable()
    {
        hatchLatchSolenoid.set(DoubleSolenoid.Value.kOff);
        hatchDeploySolenoid.set(DoubleSolenoid.Value.kOff);
    }
}