package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightSubsystem;

public class TrackBallCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final LimelightSubsystem limelightSubsystem;

    public TrackBallCommand(DriveSubsystem driveSubsystem, LimelightSubsystem limelightSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.limelightSubsystem = limelightSubsystem;
        addRequirements(driveSubsystem, limelightSubsystem);
    }

    @Override
    public void execute() {
        if (limelightSubsystem.isFound()) {
            double maxCurveSpeed = 0.4;
            double maxDriveSpeed = 0.4;

            int xDiff = limelightSubsystem.getX() - 480;
            double curveSpeed = (maxCurveSpeed / 480) * xDiff;

            double driveSpeed = (-maxDriveSpeed / 480) * xDiff + maxDriveSpeed;

            System.out.println(limelightSubsystem.getArea());

            driveSubsystem.getDiffDrive().curvatureDrive(driveSpeed, curveSpeed, false);


        }
        else{
            driveSubsystem.getDiffDrive().curvatureDrive(0, 0, false);
        }
    }

}
