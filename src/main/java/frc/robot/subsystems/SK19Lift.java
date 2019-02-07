package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Ports;
import frc.robot.subsystems.base.BaseAngleControlledArm;
import frc.robot.subsystems.base.BasePneumaticElevator;
import frc.robot.subsystems.base.BaseProximitySensor;
import frc.robot.utils.ScaledEncoder;
import frc.robot.subsystems.base.BaseGroveIRProximitySensor;

/**
 *  The SK19Lift subsystem is responsible for both the elevator and arm systems that 
 *  are responsible for moving the cargo and hatch to a set series of points.
 */
public class SK19Lift
{
    SpeedController              ArmMotor;
    BaseAngleControlledArm       RobotArm;
    BasePneumaticElevator        RobotElevator;
    BaseGroveIRProximitySensor   ElevatorDownProximitySensor;
    BaseGroveIRProximitySensor   ElevatorUpProximitySensor;
    ScaledEncoder                ArmEncoder;
    BaseProximitySensor          ArmDownLimitSensor;
    BaseProximitySensor          ArmUpLimitSensor;
    Solenoid                     ElevatorSolenoid;

    double                       ArmSpeed; 
    
    public SK19Lift()
    {
        // This is the declarations for the motor controllers, solenoids as well as the arm speed
        // TODO: Check if we are using A Talon SRX for the motor controller for the Arm
        // TODO: Use the filtered speed from the filtered joystick for the arm to be moving at
        this.ArmSpeed = 1.0;
        this.ArmMotor                    = new WPI_TalonSRX(Ports.armRotateMotor);
        this.ElevatorSolenoid            = new Solenoid(Ports.elevatorPCM);

        // This is the decleration for all of the required sensors
        this.ArmEncoder                  = new ScaledEncoder(Ports.armEncoderA, Ports.armEncoderB, Ports.intakeArmEncoderReverse, Ports.intakeArmEncoderPulsesPerRev, 1.0);
        this.ArmDownLimitSensor          = new BaseProximitySensor(Ports.armLimitBottom);
        this.ArmUpLimitSensor            = new BaseProximitySensor(Ports.armLimitTop);
        this.ElevatorUpProximitySensor   = new BaseGroveIRProximitySensor(Ports.elevatorProximityUp);
        this.ElevatorDownProximitySensor = new BaseGroveIRProximitySensor(Ports.elevatorProximityDown);

        // This is the decleration for the two base subsytems that we're using, BaseAngledControlledArm
        // As well as BasePneumaticElevator
        this.RobotArm                    = new BaseAngleControlledArm(this.ArmMotor, ArmEncoder, this.ArmUpLimitSensor, this.ArmDownLimitSensor, this.ArmSpeed);
        this.RobotElevator               = new BasePneumaticElevator(this.ElevatorSolenoid, this.ElevatorUpProximitySensor, this.ElevatorDownProximitySensor);         
    }

    //TODO: The following methods need to be finished, there may be required a 4th, 5th and 6th position
    // Need to talk to hatch team to see if positions are in proper spot
    public void setArmPosition()
    {

    }

    public void setArmPosition1()
    {

    }

    public void setArmPosition2()
    {

    }

    public void setArmPosition3()
    {

    }
}