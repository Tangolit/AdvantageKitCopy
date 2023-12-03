// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.flywheel;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
  private final FlywheelIO io;
  private final FlywheelIOInputsAutoLogged inputs = new FlywheelIOInputsAutoLogged();
  /** Creates a new Flywheel. */
  public Flywheel(FlywheelIO io) {
    this.io = io;
  }

  public double getVelocity() {
    return inputs.velocity;
  }

  public void stopFlywheel() {
    io.stop();
  }

  public void setFlywheelVelocity(double velocity) {
    io.setVelocity(velocity);
    Logger.recordOutput("Target Velocity", velocity);
  }

  public void setFlywheelVoltage(double voltage) {
    io.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);

    Logger.processInputs("Flywheel", inputs);

    Logger.recordOutput("Flywheel Speed", getVelocity());
    // This method will be called once per scheduler run
  }
}
