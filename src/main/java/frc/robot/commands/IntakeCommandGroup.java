package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.Ports;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command group implementing the sequence necessary to pull cargo into the robot.
 */
public class IntakeCommandGroup extends CommandGroup
{   
    private OI.Mode mode;

    /**
     *   
     *   
     **/
    public IntakeCommandGroup(OI.Mode mode)
    {
        requires(Robot.Intake);
        this.mode   = mode;

        addSequential(new IntakeRollersCommand(mode, true, false));
        addSequential(new IntakeArmPositionCommand(mode, Ports.intakeArmDeployedAngle));
        addSequential(new IntakeWaitForCargoCommand(mode, true));
        addSequential(new IntakeArmPositionCommand(mode, Ports.intakeArmStowedAngle));
        addSequential(new IntakeRollersCommand(mode, false, false));
    }

    public boolean isInterruptible()
    {
        return true;
    }
}
