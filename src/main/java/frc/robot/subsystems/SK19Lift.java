package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Ports;
import frc.robot.utils.ScaledEncoder;
import frc.robot.subsystems.base.*;

/**
 *  The SK19Lift subsystem is responsible for both the elevator and arm systems that 
 *  are responsible for moving the cargo and hatch to a set series of points.
 */
public class SK19Lift extends Subsystem
{
    SpeedController                     ArmMotor;
    SpeedController                     octopusMotor;
    BaseAngleControlledArm              RobotArmAngled;
    BasePneumaticElevator               RobotElevator;
    BaseGroveIRProximitySensor          ElevatorDownProximitySensor;
    BaseGroveIRProximitySensor          ElevatorUpProximitySensor;
    SPIEncoderAMT203V                   ArmEncoder;
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
    int                                 lastPosition;
    double                              octopusScaler;

    /*  This is the lookup table for the required values for the elevator and arm. The first row is the double values that need to be converted to booleans for the elevator.
    *   The next row is the doubles required for the hatch placement. The third row is the doubles required for cargo placement.
    *   TODO: The actual values for the hatch and the cargo placement will need to be verified at a later point.
    */
        private final SK19LiftLookup[][] lookupTable = {
            {new SK19LiftLookup(Ports.ElevatorPosition0, Ports.ArmPosition0), new SK19LiftLookup(Ports.ElevatorPosition0, Ports.ArmPosition0)},
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
        this.octopusScaler = 0.6;
        this.ArmMotor                    = new WPI_TalonSRX(Ports.armRotateMotor);
        this.octopusMotor                = new WPI_TalonSRX(Ports.octopusMotor);
        this.ElevatorSolenoid            = new Solenoid(Ports.elevatorPCM);
        this.HatchDeploySolenoid         = new DoubleSolenoid(Ports.hatchGripperOut, Ports.hatchGripperIn);
        this.HatchLockSolenoid           = new DoubleSolenoid(Ports.hatchGripperLock, Ports.hatchGripperUnlock);
      

        // This is the decleration for all of the required sensors
        this.ArmEncoder                  = new SPIEncoderAMT203V(Ports.armEncoderSPI, Ports.intakeArmEncoderPulsesPerRev);
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
        this.OctopusRoller               = new BaseOctopusRoller(this.BallSensor, this.octopusMotor, this.octopusScaler);
    }

    //TODO: The following methods need to be finished, there may be required a 4th, 5th and 6th position
    // Need to talk to hatch team to see if positions are in proper spot
    public void setArmPosition(int posIndex)
    {   
        double setAngle = 0.0;
        boolean setPosition = false;
        boolean hatchSensor = this.HatchSensor.getIsTriggered();
        boolean ballSensor = this.BallSensor.getIsTriggered();
        
        if (hatchSensor && !ballSensor)
        {   
            setAngle = lookupTable[posIndex][0].armAngle;
            setPosition = lookupTable[posIndex][0].ElevatorUp;
            lastPosition = posIndex;
        }
        else if (ballSensor && !hatchSensor)
        {
            setAngle = lookupTable[posIndex][1].armAngle;
            setPosition = lookupTable[posIndex][1].ElevatorUp;
            lastPosition = posIndex;
        }
        else
        {
            int doubleClickedIndex = lastPosition == posIndex ? 1: 0;
            setAngle = lookupTable[posIndex][doubleClickedIndex].armAngle;
            setPosition = lookupTable[posIndex][doubleClickedIndex].ElevatorUp;
            posIndex = 0;
        }
        RobotArmAngled.moveToAngle(setAngle);
        RobotElevator.setPosition(setPosition);
    }

    public void testSetArmPositionMotorSpeed(double speed)
    {
        RobotArmAngled.setSpeed(speed);
    }

    public void testSetElevatorPosition(boolean ElevUp)
    {
        RobotElevator.setPosition(ElevUp);
    }

    public void initDefaultCommand()
    {

    }
    public void HatchGripper(boolean gripperLock)
    {
        if (gripperLock)
        {
            RobotHatch.latchHatch();
        }
        else
        {
            RobotHatch.unlatchHatch();
        }
    }

    public void HatchPusher(boolean pusherExtend)
    {
        if (pusherExtend)
        {
            RobotHatch.extendHatchPiston();
        }
        else
        {
            RobotHatch.retractHatchPiston();
        }
    }

}