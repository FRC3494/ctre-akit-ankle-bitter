package frc.robot.subsystems;

import java.util.function.Consumer;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimelightHelpers;

public class Vision extends SubsystemBase{
    boolean hasTarget;
    double tx;
    double ty;
    double ta;

    double txnc;
    double tync;

    String limelight;

    Consumer<Pose2d> addMeasurement;

    public Vision(Consumer<Pose2d> addMeasurement) {
        this.addMeasurement = addMeasurement;
    }

    @Override
    public void periodic() {
        hasTarget = LimelightHelpers.getTV(limelight); // Do you have a valid target?
        tx = LimelightHelpers.getTX(limelight); // Horizontal offset from crosshair to target in degrees
        ty = LimelightHelpers.getTY(limelight); // Vertical offset from crosshair to target in degrees
        ta = LimelightHelpers.getTA(limelight); // Target area (0% to 100% of image)

        txnc = LimelightHelpers.getTXNC(limelight); // Horizontal offset from principal pixel/point to target in degrees
        tync = LimelightHelpers.getTYNC(limelight); // Vertical offset from principal pixel/point to target in degrees
    }
}
