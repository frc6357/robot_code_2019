package frc.robot.subsystems.base;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.subsystems.base.BaseLimitSensor;

/**
 *  Base class that deploys the hatch and extends the hatch mechanism
 */
 public class BaseHatchMechanism
 {
    // Solenoids
    private final Solenoid hatchRocketDeploySolenoid;
    private final Solenoid hatchDeploymentSolenoid;

    // Limit Sensors
    private final BaseLimitSensor hatchDetector;
    private final BaseLimitSensor hatchMechanismSensor;

    /**
     *  Intializes the required solenoids and sensors
     *  @param hatchRocketDeploySolenoid
     *     - Type: Solenoid
     *     - Controls the slider mechanism that moves the hatch to the rocket when retracted
     *  @param hatchDeploymentSolenoid
     *     - Type: Solenoid
     *     - Controls the hatch mechanism to either open or close the mechanism
     *  @param hatchDetector
     *     - Type: BaseLimitSensor
     *     - Detects whether the hatch is on the mechanism or not
     *  @param hatchMechanismSensor
     *     - Type: BaseLimitSensor
     *     - Detects whether the mechanism is in an open position or not
     */
    public BaseHatchMechanism(Solenoid hatchRocketDeploySolenoid, Solenoid hatchDeploymentSolenoid, BaseLimitSensor hatchDetector, BaseLimitSensor hatchMechanismSensor)
    {
        this.hatchRocketDeploySolenoid= hatchRocketDeploySolenoid;
        this.hatchDeploymentSolenoid = hatchDeploymentSolenoid;
        this.hatchDetector = hatchDetector;
        this.hatchMechanismSensor = hatchMechanismSensor;
    }

    /**
     *  Sets solenoid to be extended and deploys hatch to rocket
     */
    public void extendToRocket()
    {
        hatchRocketDeploySolenoid.set(true);
    }

    /**
     *  Sets hatch holder mechanism to extended to lock hatch
     */
    public void latchHatch()
    {
        hatchDeploymentSolenoid.set(true);
    }

    /**
     *  Retracts piston pushing the hatch to the rocket
     */
    public void retractFromRocket()
    {
        hatchRocketDeploySolenoid.set(false);
    }

    /**
     *  Retracts the piston holding the hatch, therefore unlatching the hatch
     */
    public void unlatchHatch()
    {
        hatchDeploymentSolenoid.set(false);
    }

    /**
     *  Detects whether the mechanism that holds the hatch is open or closed
     *  @return
     *      - Type: boolean
     *      - Will be true if mechanism is open, or false if mechanism is latched
     */
    public boolean hatchMechanismOpen()
    {
        return hatchMechanismSensor.getIsTriggered();
    }

    /**
     *  Detects whether there is a hatch on the mechanism
     *  @return
     *      - Type: boolean
     *      - Will be true if hatch is on sensor, and false if no hatch is on the sensor
     */
    public boolean hatchDetector()
    {
        return hatchDetector.getIsTriggered();
    }
 }