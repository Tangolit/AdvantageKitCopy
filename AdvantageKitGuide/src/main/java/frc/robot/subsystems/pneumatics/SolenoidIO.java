package frc.robot.subsystems.pneumatics;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface SolenoidIO {
    @AutoLog
    public static class SolenoidIOInputs {
        public Value val = Value.kOff;
    }

    public default void updateInputs(SolenoidIOInputs inputs) {}

    public default void set(Value value) {}
}
