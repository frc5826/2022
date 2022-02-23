package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TrackBallCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    public TrackBallCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
    }


    @Override
    public void execute() {

    }

}
