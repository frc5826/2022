package frc.robot.subsystems;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

    private CANSparkMax leftSpark1;
    private CANSparkMax leftSpark2;
    private CANSparkMax rightSpark1;
    private CANSparkMax rightSpark2;

    private MotorControllerGroup leftSpeedControllers;
    private MotorControllerGroup rightSpeedControllers;

    private double distance;
    private long timeStamp;

    private int count = 0;

    private SparkMaxPIDController leftSpark1_PID;
    private SparkMaxPIDController leftSpark2_PID;
    private SparkMaxPIDController rightSpark1_PID;
    private SparkMaxPIDController rightSpark2_PID;

    private static final int leftID1 = 0;
    private static final int leftID2 = 0;
    private static final int rightID1 = 0;
    private static final int rightID2 = 0;

    public DriveSubsystem() {

        leftSpark1 = new CANSparkMax(leftID1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSpark2 = new CANSparkMax(leftID2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark1 = new CANSparkMax(rightID1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSpark2 = new CANSparkMax(rightID2, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftSpark1.restoreFactoryDefaults();
        leftSpark2.restoreFactoryDefaults();
        rightSpark1.restoreFactoryDefaults();
        rightSpark2.restoreFactoryDefaults();

        leftSpark1.setInverted(false);
        leftSpark2.setInverted(false);
        rightSpark1.setInverted(true);
        rightSpark2.setInverted(true);

        leftSpark2.follow(leftSpark1);
        rightSpark2.follow(rightSpark1);

        differentialDrive = new DifferentialDrive(leftSpark1, rightSpark1);
        differentialDrive.setSafetyEnabled(false);
    }

    private DifferentialDrive differentialDrive;

    public DifferentialDrive getDiffDrive(){

        return differentialDrive;
    }

}
