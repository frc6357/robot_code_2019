package frc.robot.subsystems;

import java.util.ArrayList;

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
    private final ArrayList<Boolean>    BoolArrayList;
    private final ArrayList<Double>    IntHatchArrayList;
    private final ArrayList<Double>    IntBallArrayList;
    private final ArrayList<ArrayList>  LookupTable;
    /**
     *  Creates the Arm and elevator base classes and seamlessly melds them together so that no matter
     *  the position that we wish to be at it is a one button click and it automatically goes to the required
     *  position.
     */
    public SK19Lift()
    {
        // This is the declarations for the motor controllers, solenoids as well as the arm speed
        // TODO: Check if we are using A Talon SRX for the motor controller for the Arm
        // TODO: Use the filtered speed from the filtered joystick for the arm to be moving at
        this.ArmSpeed = 1.0;
        this.ArmMotor                    = new WPI_TalonSRX(Ports.armRotateMotor);
        this.ElevatorSolenoid            = new Solenoid(Ports.elevatorPCM);
        this.HatchDeploySolenoid         = new DoubleSolenoid(Ports.hatchGripperOut, Ports.hatchGripperIn);
        this.HatchLockSolenoid           = new DoubleSolenoid(Ports.hatchGripperLock, Ports.hatchGripperUnlock);
        // TODO: This may be converted to a static intiation for a static Array not an array list. This is a Work in Progress and will need to be fixed, come back to first thing on Saturday
        // To get the answer but it should be converted to double numbers and then use an if statement to get the required boolean out of it.
        BoolArrayList = new ArrayList<Boolean>();
        BoolArrayList.add(false);
        BoolArrayList.add(false);
        BoolArrayList.add(true);
        IntBallArrayList = new ArrayList<Double>();
        IntBallArrayList.add(5.0);
        IntBallArrayList.add(50.0);
        IntBallArrayList.add(95.0);
        IntHatchArrayList = new ArrayList<Double>();
        IntHatchArrayList.add(0.0);
        IntHatchArrayList.add(45.0);
        IntHatchArrayList.add(90.0);
        LookupTable = new ArrayList<ArrayList>();
        LookupTable.add(BoolArrayList);
        LookupTable.add(IntBallArrayList);
        LookupTable.add(IntHatchArrayList);

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
    public void setArmPosition()
    {

    }

    public void setArmPosition1()
    {
        final ArrayList<Double> PulledDoubleArray;
        Double PulledDouble;
        if (this.HatchSensor.getIsTriggered())
        {   
            PulledDoubleArray = LookupTable.get(2);
            PulledDouble = (PulledDoubleArray.get(0));
            RobotArmAngled.moveToAngle(PulledDouble);
            RobotElevator.setToDown();
        }
        else if (this.BallSensor.getIsTriggered())
        {
            PulledDoubleArray = LookupTable.get(1);
            PulledDouble = PulledDoubleArray.get(0);
            RobotArmAngled.moveToAngle(PulledDouble);
            RobotElevator.setToDown();
        }
    }

    public void setArmPosition2()
    {
        RobotArmAngled.moveToAngle(0.0);
        RobotElevator.setToUp();
    }

    public void setArmPosition3()
    {
        RobotArmAngled.moveToAngle(90.0);
        RobotElevator.setToUp();
    }
}