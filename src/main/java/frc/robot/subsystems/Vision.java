package frc.robot.subsystems;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.LimelightHelpers;
import frc.robot.util.LimelightHelpers.PoseEstimate;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Vision extends SubsystemBase {
  private boolean hasTarget;
  private double tx;
  private double ty;
  private double ta;

  private double txnc;
  private double tync;

  private final String limelight;

  private final BiConsumer<Pose2d, Double> addMeasurement;
  private final Supplier<Rotation2d> robotYaw;

  private final Pose3d cameraPose =
      new Pose3d(
          Inches.of(-9.75),
          Inches.of(-2.25),
          Inches.of(11.0),
          new Rotation3d(Degrees.of(0.0), Degrees.of(0.0), Degrees.of(0.0)));

  public Vision(BiConsumer<Pose2d, Double> addMeasurement, Supplier<Rotation2d> robotYaw) {
    this.limelight = "limelight-avh";
    this.addMeasurement = addMeasurement;
    this.robotYaw = robotYaw;
    LimelightHelpers.setCameraPose_RobotSpace(
        limelight,
        cameraPose.getMeasureX().in(Meters),
        cameraPose.getMeasureY().in(Meters),
        cameraPose.getMeasureZ().in(Meters),
        cameraPose.getRotation().getMeasureX().in(Degrees),
        cameraPose.getRotation().getMeasureY().in(Degrees),
        cameraPose.getRotation().getMeasureZ().in(Degrees));
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

    LimelightHelpers.SetRobotOrientation(limelight, robotYaw.get().getDegrees(), 0, 0, 0, 0, 0);
    PoseEstimate estimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(limelight);
    if (estimate.tagCount > 0) {
      addMeasurement.accept(estimate.pose, estimate.timestampSeconds);
    }
  }
}
