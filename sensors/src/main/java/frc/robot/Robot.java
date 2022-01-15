// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Units;

import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor.Port;

public class Robot extends TimedRobot
{

    private Rev2mDistanceSensor dist2m = new Rev2mDistanceSensor(Port.kOnboard);
    LidarLitePWM lidar = new LidarLitePWM(new DigitalInput(0));
    AnalogInput sonar = new AnalogInput(0);

    @Override
    public void robotInit()
    {

    }

    @Override
    public void robotPeriodic()
    {

    }

    @Override
    public void autonomousInit()
    {
        dist2m.setAutomaticMode(true);
    }

    @Override
    public void autonomousPeriodic()
    {
        double sonarRaw = sonar.getValue();
        double voltageScaleFactor = 5/RobotController.getVoltage5V();

        double sonarDistInches = sonarRaw * voltageScaleFactor * 0.0492;

        System.out.println("Lidar: " + Units.metersToInches(lidar.getDistance() / 100));
        System.out.println("Sonar: " + sonarDistInches);
        if(dist2m.isRangeValid()) {
            System.out.println("2m: " + dist2m.getRange(Rev2mDistanceSensor.Unit.kInches));
        }

    }

    @Override
    public void teleopInit()
    {
        
    }

    @Override
    public void teleopPeriodic()
    {
        
    }

    @Override
    public void disabledInit()
    {
        dist2m.setAutomaticMode(false);
    }

    @Override
    public void disabledPeriodic()
    {

    }

    @Override
    public void testInit()
    {
        
    }

    @Override
    public void testPeriodic()
    {
        
    }
}
