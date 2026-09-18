package frc.robot.commands;

import static edu.wpi.first.wpilibj2.command.Commands.*;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import frc.robot.generated.ChoreoTraj;

public class VisionTestAuto {
  public static final String name = "VisionTest";

  public VisionTestAuto() {}

  public static AutoRoutine getRoutine(AutoFactory autoFactory) {
    AutoRoutine routine = autoFactory.newRoutine(name);

    AutoTrajectory trajectory = ChoreoTraj.VisionTest.asAutoTraj(routine);

    routine.active().onTrue(sequence(trajectory.resetOdometry(), trajectory.cmd()));

    return routine;
  }
}
