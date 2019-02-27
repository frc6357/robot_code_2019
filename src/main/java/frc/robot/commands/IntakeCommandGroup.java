package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.OI;
import frc.robot.TuningParams;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command group implementing the sequence necessary to pull cargo into the robot.
 */
public class IntakeCommandGroup extends CommandGroup
{
    /**
     *
     *
     **/
    public IntakeCommandGroup(OI.Mode mode)
    {
        requires(Robot.Intake);
        requires(Robot.Lift);

        addSequential(new IntakeRollersCommand(mode, true, false));
        addSequential(new IntakeArmPositionCommand(mode, TuningParams.intakeArmDeployedAngle));
        addSequential(new IntakeWaitForCargoCommand(mode, true));
        addSequential(new IntakeArmPositionCommand(mode, TuningParams.intakeArmStowedAngle));
        addSequential(new IntakeRollersCommand(mode, false, false));
    }

    public boolean isInterruptible()
    {
        return true;
    }
}
