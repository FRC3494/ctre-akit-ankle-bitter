package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimelightHelpers;
import frc.robot.util.LimelightHelpers.PoseEstimate;
import java.util.function.BiConsumer;

public class Vision extends SubsystemBase {
  private boolean hasTarget;
  private double tx;
  private double ty;
  private double ta;

  private double txnc;
  private double tync;

  private final String limelight;

  private final BiConsumer<Pose2d, Double> addMeasurement;

  public Vision(BiConsumer<Pose2d, Double> addMeasurement) {
    this.limelight = "limelight-avh";
    this.addMeasurement = addMeasurement;
  }

  @Override
  public void periodic() {
    hasTarget = LimelightHelpers.getTV(limelight); // Do you have a valid target?
    tx = LimelightHelpers.getTX(limelight); // Horizontal offset from crosshair to target in degrees
    ty = LimelightHelpers.getTY(limelight); // Vertical offset from crosshair to target in degrees
    ta = LimelightHelpers.getTA(limelight); // Target area (0% to 100% of image)

    txnc =
        LimelightHelpers.getTXNC(
            limelight); // Horizontal offset from principal pixel/point to target in degrees
    tync =
        LimelightHelpers.getTYNC(
            limelight); // Vertical offset from principal pixel/point to target in degrees

    PoseEstimate estimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(limelight);
    addMeasurement.accept(estimate.pose, estimate.timestampSeconds);
  }
}
