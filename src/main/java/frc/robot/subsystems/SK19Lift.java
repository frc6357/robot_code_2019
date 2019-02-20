package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Ports;
import frc.robot.utils.ScaledEncoder;
import frc.robot.subsystems.base.*;
import frc.robot.TuningParams;

/**
 *  The SK19Lift subsystem is responsible for both the elevator and arm systems that
 *  are responsible for moving the cargo and hatch to a set series of points.
 */
public class SK19Lift extends Subsystem
{
    private SpeedController                     octopusMotor;
    private SpeedController                     ArmMotor;
    private BaseAngleControlledArm              RobotArmAngled;
    private BasePneumaticElevator               RobotElevator;
    private BaseGroveIRProximitySensor          ElevatorDownProximitySensor;
    private BaseGroveIRProximitySensor          ElevatorUpProximitySensor;
    private ScaledEncoder                       ArmEncoder;
    private BaseProximitySensor                 ArmDownLimitSensor;
    private BaseProximitySensor                 ArmUpLimitSensor;
    private Solenoid                            ElevatorSolenoid;
    private BaseOctopusRoller                   OctopusRoller;
    private BaseHatchMechanism                  RobotHatch;
    private DoubleSolenoid                      HatchLockSolenoid;
    private DoubleSolenoid                      HatchDeploySolenoid;
    private BaseMotorizedArm                    robotArmMotorized;

    private int                                 lastPosition;
    private double                              octopusScaler;
    private int                                 cargoIndexSearch = 0;
    private int                                 hatchIndexSearch = 1;

    public BaseProximitySensor                  HatchSensor;
    public BaseProximitySensor                  BallSensor;


    /*  This is the lookup table for the required values for the elevator and arm. The first row is the double values that need to be converted to booleans for the elevator.
    *   The next row is the doubles required for the hatch placement. The third row is the doubles required for cargo placement.
    *   TODO: The actual values for the hatch and the cargo placement will need to be verified at a later point.
    */
        private final SK19LiftLookup[][] lookupTable = 
        {
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
        this.octopusScaler = 0.6;

        this.ArmMotor                    = new CANSparkMax(Ports.armRotateMotor, MotorType.kBrushless);
        this.octopusMotor                = new WPI_TalonSRX(Ports.octopusMotor);
        this.ElevatorSolenoid            = new Solenoid(Ports.pcm1, Ports.elevatorPCM);
        this.HatchDeploySolenoid         = new DoubleSolenoid(Ports.pcm1, Ports.hatchGripperOut, Ports.hatchGripperIn);
        this.HatchLockSolenoid           = new DoubleSolenoid(Ports.pcm1, Ports.hatchGripperLock, Ports.hatchGripperUnlock);

        // This is the decleration for all of the required sensors
        this.ArmEncoder                  = new ScaledEncoder(Ports.armEncoderA, Ports.armEncoderB, Ports.intakeArmEncoderPulsesPerRev, Ports.armEncoderDiameter);
        this.ArmDownLimitSensor          = new BaseProximitySensor(Ports.armLimitBottom);
        this.ArmUpLimitSensor            = new BaseProximitySensor(Ports.armLimitTop);
        this.ElevatorUpProximitySensor   = new BaseGroveIRProximitySensor(Ports.elevatorProximityUp);
        this.ElevatorDownProximitySensor = new BaseGroveIRProximitySensor(Ports.elevatorProximityDown);
        this.HatchSensor                 = new BaseProximitySensor(Ports.hatchContactSwitch);
        this.BallSensor                  = new BaseProximitySensor(Ports.octopusCargoDetect);

        // This is the decleration for the two base subsytems that we're using, BaseAngledControlledArm
        // As well as BasePneumaticElevator
        this.robotArmMotorized           = new BaseMotorizedArm(this.ArmMotor, this.ArmUpLimitSensor, this.ArmDownLimitSensor);
        this.RobotArmAngled              = new BaseAngleControlledArm(this.robotArmMotorized, ArmEncoder, TuningParams.LiftArmPValue, TuningParams.LiftArmIValue, TuningParams.LiftArmDValue, TuningParams.LiftArmToleranceValue, TuningParams.LiftArmInvertMotor);
        this.RobotElevator               = new BasePneumaticElevator(this.ElevatorSolenoid, this.ElevatorUpProximitySensor, this.ElevatorDownProximitySensor);
        this.RobotHatch                  = new BaseHatchMechanism(this.HatchDeploySolenoid, this.HatchLockSolenoid, this.HatchSensor);
        this.OctopusRoller               = new BaseOctopusRoller(this.BallSensor, this.octopusMotor, this.octopusScaler);
    }

    /**
     *  This takes the passed integer position and checks if there is a hatch or a piece of cargo, and if there is goes to the appropriate position, if there is no hatch or cargo
     *  then it goes to the lowest position of the set points, and if pressed again will go to the highest position of the two positions
     *  @param posIndex
     *      - Type: int
     *      - This is the passed index that controls what position the arm is going to move to
     */
    public void setArmPosition(int posIndex)
    {
        double setAngle     = 0.0;
        boolean setPosition = false;
        boolean hatchSensor = this.HatchSensor.getIsTriggered();
        boolean ballSensor  = this.BallSensor.getIsTriggered();

        if (hatchSensor && !ballSensor)
        {
            setAngle = lookupTable[posIndex][cargoIndexSearch].armAngle;
            setPosition = lookupTable[posIndex][cargoIndexSearch].ElevatorUp;
            lastPosition = posIndex;
        }
        else if (ballSensor && !hatchSensor)
        {
            setAngle = lookupTable[posIndex][hatchIndexSearch].armAngle;
            setPosition = lookupTable[posIndex][hatchIndexSearch].ElevatorUp;
            lastPosition = posIndex;
        }
        else
        {
            int doubleClickedIndex = lastPosition == posIndex ? 1: 0;
            setAngle = lookupTable[posIndex][doubleClickedIndex].armAngle;
            setPosition = lookupTable[posIndex][doubleClickedIndex].ElevatorUp;
            posIndex = 0;
        }
        RobotArmAngled.moveToAngleDegrees(setAngle);
        RobotElevator.setPosition(setPosition);
    }

    /**
     *  This method that takes a double value and sets the arm motor controller to a specific speed to be moving at.
     *  @param speed
     *      - Type: double
     *      - This double value if positive up to 1.0 sets the motor to run forwards at that speed. If the value is negative then the motor will turn backwards at that set speed.
     */
    public void testSetArmPositionMotorSpeed(double speed)
    {
        this.ArmMotor.set(speed);
        //robotArmMotorized.setSpeed(speed);
    }

    /**
     *  This sets the elevator position to either up or false based on the boolean parameter
     *  @param ElevUp
     *      - Type: boolean
     *      - If this is true it sets the elevator to the highest position and if false it sets it to the lowest position
     */
    public void SetElevatorPosition(boolean ElevUp)
    {
        RobotElevator.setPosition(ElevUp);
    }

    public void initDefaultCommand()
    {

    }

    /**
     *  This method takes a boolean value about whether to extend the and lock the hatch gripper, or to unlockand retract the hatch gripper
     *  @param gripperLock
     *      - Type: Boolean
     *      - If this value is true the piston will extend and lock a hatch in place, if the value is false the piston will retract and will unlock the locking mechanism.
     */
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

    /**
     *  This method takes a boolean value and decides whether to extend the pushing mechanism on the hatch mechanism to push off a hatch, or to retract it and keep the hatch on
     *  @param pusherExtend
     *      - Type: boolean
     *      - This value decides whether the piston that pushes off the hatch is extended or not, if the value is true the piston will extend, and if it's false the piston will retract.
     */
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

    /**
     *  This method takes a double value and runs a comparison on it to see if it's greater than, less than or equal to zero
     *  @param cargoSpeed
     *      - Type double:
     *      - This value should either be something greater than zero, less than zero or at zero and the cargo roller will be set to either forwards, backwards or will stop depending on the value
     */
    public void cargoSystem(double cargoSpeed)
    {
        if (cargoSpeed > 0)
        {
            OctopusRoller.setForwards();
        }
        else if (cargoSpeed < 0)
        {
            OctopusRoller.setBackwards();
        }
        else
        {
            OctopusRoller.setStop();
        }
    }

    // Move the lift subsystem arm. This is set up such that a joystick value of 90% or higher
    // bumps the setpoint up one degree and input of -90% bumps it down a degree.
    public void UpdateArmPosition(double axis)
    {
        double currentSetpoint;
        double delta = 0.0;

        // Ignore any input where the joystick isn't near the top or bottom (wide deadband!)
        if((axis > -0.9) && (axis < 0.9))
            return;

        currentSetpoint = RobotArmAngled.getArmSetpoint();

        currentSetpoint += ((axis > 0.9) ? 1.0 : -1.0);

        RobotArmAngled.moveToAngleDegrees(currentSetpoint);
    }

    // Move the arm to a specific angle.
    public void SetArmPositionDegrees(double angle)
    {
        RobotArmAngled.moveToAngleDegrees(angle);
    }
}