package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveSubsystem;

public class TrackBallCommand extends CommandBase {

    private final DriveSubsystem driveSubsystem;

    public TrackBallCommand(DriveSubsystem driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem);
        setTeam();
    }

    public void setTeam(){
        double[] llrobot = new double[]{DriverStation.getAlliance().ordinal(),0,0,0,0,0,0,0};
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("llrobot").setDoubleArray(llrobot);
    }

    @Override
    public void execute() {

        setTeam();
        double[] llpython = NetworkTableInstance.getDefault().getTable("limelight").getEntry("llpython").getDoubleArray(new double[]{0,0,0,0,0,0,0,0});

        boolean ballDetected = (int)llpython[0] == 1;

        double area = llpython[1];
        int x = (int)llpython[2];
        int y = (int)llpython[3];

        System.out.println(DriverStation.getAlliance().ordinal());
    }

}
