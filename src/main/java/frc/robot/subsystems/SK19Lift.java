package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Ports;
import frc.robot.subsystems.base.BaseAngleControlledArm;
import frc.robot.subsystems.base.BasePneumaticElevator;
import frc.robot.subsystems.base.BaseProximitySensor;
import frc.robot.utils.ScaledEncoder;
import frc.robot.subsystems.base.BaseGroveIRProximitySensor;
import frc.robot.subsystems.base.BaseHatchMechanism;
import frc.robot.subsystems.base.BaseOctopusRoller;

/**
 *  The SK19Lift subsystem is responsible for both the elevator and arm systems that 
 *  are responsible for moving the cargo and hatch to a set series of points.
 */
public class SK19Lift
{
    SpeedController                     ArmMotor;
    BaseAngleControlledArm              RobotArmAngled;
    BasePneumaticElevator               RobotElevator;
    BaseGroveIRProximitySensor          ElevatorDownProximitySensor;
    BaseGroveIRProximitySensor          ElevatorUpProximitySensor;
    ScaledEncoder                       ArmEncoder;
    BaseProximitySensor                 ArmDownLimitSensor;
    BaseProximitySensor                 ArmUpLimitSensor;
    Solenoid                            ElevatorSolenoid;
    BaseOctopusRoller                   OctopusRoller;
    BaseHatchMechanism                  RobotHatch;
    DoubleSolenoid                      HatchLockSolenoid;
    DoubleSolenoid                      HatchDeploySolenoid;
    BaseProximitySensor                 HatchSensor;
    BaseProximitySensor                 BallSensor;

    double                              ArmSpeed;

    /*  This is the lookup table for the required values for the elevator and arm. The first row is the double values that need to be converted to booleans for the elevator.
    *   The next row is the doubles required for the hatch placement. The third row is the doubles required for cargo placement.
    *   TODO: The actual values for the hatch and the cargo placement will need to be verified at a later point.
    */
    private final double[][] LookupTable = {
        {0.0, 1.0, 1.0},
        {0.0, 45.0, 90.0},
        {5.0, 50.0, 95.0}
    };
        private final SK19LiftLookup[][] lookupTable = {
            {new SK19LiftLookup(Ports.ElevatorPosition1, Ports.ArmPositionHatch1), new SK19LiftLookup(Ports.ElevatorPosition1, Ports.ArmPostionCargo1)},
            {new SK19LiftLookup(Ports.ElevatorPosition2, Ports.ArmPositionHatch2), new SK19LiftLookup(Ports.ElevatorPosition2, Ports.ArmPositionCargo2)},
            {new SK19LiftLookup(Ports.ElevatorPosition3, Ports.ArmPositionHatch3), new SK19LiftLookup(Ports.ElevatorPosition3, Ports.ArmPositionCargo3)}
        };
    /**
     *  Creates the Arm and elevator base classes and seamlessly melds them together so that no matter
     *  the position that we wish to be at it is a one button click and it automatically goes to the required
     *  position.
     */
    public SK19Lift()
    {
        // This is the declarations for the motor controllers, solenoids as well as the arm speed
        // TODO: Check if we are using A Talon SRX for the motor controller for the Arm
        this.ArmSpeed = 1.0;
        this.ArmMotor                    = new WPI_TalonSRX(Ports.armRotateMotor);
        this.ElevatorSolenoid            = new Solenoid(Ports.elevatorPCM);
        this.HatchDeploySolenoid         = new DoubleSolenoid(Ports.hatchGripperOut, Ports.hatchGripperIn);
        this.HatchLockSolenoid           = new DoubleSolenoid(Ports.hatchGripperLock, Ports.hatchGripperUnlock);
      

        // This is the decleration for all of the required sensors
        this.ArmEncoder                  = new ScaledEncoder(Ports.armEncoderA, Ports.armEncoderB, Ports.intakeArmEncoderReverse, Ports.intakeArmEncoderPulsesPerRev, 1.0);
        this.ArmDownLimitSensor          = new BaseProximitySensor(Ports.armLimitBottom);
        this.ArmUpLimitSensor            = new BaseProximitySensor(Ports.armLimitTop);
        this.ElevatorUpProximitySensor   = new BaseGroveIRProximitySensor(Ports.elevatorProximityUp);
        this.ElevatorDownProximitySensor = new BaseGroveIRProximitySensor(Ports.elevatorProximityDown);
        this.HatchSensor                 = new BaseProximitySensor(Ports.hatchContactSwitch);
        this.BallSensor                  = new BaseProximitySensor(Ports.octopusCargoDetect);
        // This is the decleration for the two base subsytems that we're using, BaseAngledControlledArm
        // As well as BasePneumaticElevator
        this.RobotArmAngled              = new BaseAngleControlledArm(this.ArmMotor, ArmEncoder, this.ArmUpLimitSensor, this.ArmDownLimitSensor, this.ArmSpeed);
        this.RobotElevator               = new BasePneumaticElevator(this.ElevatorSolenoid, this.ElevatorUpProximitySensor, this.ElevatorDownProximitySensor);     
        this.RobotHatch                  = new BaseHatchMechanism(this.HatchDeploySolenoid, this.HatchLockSolenoid, this.HatchSensor);
    }

    //TODO: The following methods need to be finished, there may be required a 4th, 5th and 6th position
    // Need to talk to hatch team to see if positions are in proper spot
    public void setArmPosition(int posIndex)
    {   
        double setAngle = 0.0;
        boolean setPosition = false;
        if (this.HatchSensor.getIsTriggered())
        {   
            RobotArmAngled.moveToAngle(lookupTable[0][0].armAngle);
            RobotElevator.setPosition(lookupTable[0][0].ElevatorUp);
        }
        else if (this.BallSensor.getIsTriggered())
        {
        
        }
        RobotArmAngled.moveToAngle(setAngle);
        RobotElevator.setPosition(setPosition);
    }

    public void testSetArmPositionMotorSpeed(double speed)
    {
        // TODO: Implement code here for direct arm drive
    }

    public void testSetElevatorPosition(boolean ElevUp)
    {
        // TODO: Set the state directly for test mode for elevator
    }
}