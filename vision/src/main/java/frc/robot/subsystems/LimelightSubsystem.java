package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
    private boolean found;
    private int x;
    private int y;
    private double area;

    public LimelightSubsystem() {

    }

    public boolean isFound() {
        return found;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getArea() {
        return area;
    }

    @Override
    public void periodic() {
        setTeam();

        double[] llpython = NetworkTableInstance.getDefault().getTable("limelight").getEntry("llpython").getDoubleArray(new double[]{0,0,0,0,0,0,0,0});

        found = (int)llpython[0] == 1;

        area = llpython[1];
        x = (int)llpython[2];
        y = (int)llpython[3];

        super.periodic();
    }

    public void setTeam(){
        double[] llrobot = new double[]{DriverStation.getAlliance().ordinal(),0,0,0,0,0,0,0};
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("llrobot").setDoubleArray(llrobot);
    }

}
