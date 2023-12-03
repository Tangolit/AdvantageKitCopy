// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  private final MotorControllerIO pivot;
  private final MotorControllerIO roller;
  private final MotorControllerIOInputsAutoLogged inputs = new MotorControllerIOInputsAutoLogged();
  
  /** Creates a new Intake. */
  public Intake(MotorControllerIO pivot, MotorControllerIO roller) {
    this.pivot = pivot;
    this.roller = roller;
  }

  @Override
  public void periodic() {
    pivot.updateInputs(inputs);

    Logger.processInputs("Intake", inputs);
    // This method will be called once per scheduler run
  }

  public void runRollers(double velocity) {
    roller.setVelocity(velocity);
  }

  public void stopRollers() {
    roller.stop();
  }

  public void stopPivot() {
    pivot.stop();
  }

  public void setPosition(double position) {
    pivot.setPosition(position);

    Logger.recordOutput("Intake Target", position);
  }
}
