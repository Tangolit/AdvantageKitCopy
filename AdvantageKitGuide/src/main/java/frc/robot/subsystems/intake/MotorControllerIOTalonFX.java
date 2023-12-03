package frc.robot.subsystems.intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;

public class MotorControllerIOTalonFX implements MotorControllerIO {
    private final TalonFX falcon;

    private final StatusSignal<Double> pivotPosition;
    private final StatusSignal<Double> pivotVelocity;
    private final StatusSignal<Double> appliedVolts;
    private final StatusSignal<Double> currentAmps;

    public MotorControllerIOTalonFX(int id) {
        falcon = new TalonFX(id);

        pivotPosition = falcon.getPosition();
        pivotVelocity = falcon.getVelocity();
        appliedVolts = falcon.getMotorVoltage();
        currentAmps = falcon.getStatorCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(100, pivotPosition, pivotVelocity, appliedVolts, currentAmps);

    }

    @Override
    public void updateInputs(MotorControllerIOInputs inputs) {
        inputs.pivotPosition = Units.rotationsToDegrees(pivotPosition.getValueAsDouble());
        inputs.pivotVelocity = Units.rotationsPerMinuteToRadiansPerSecond(pivotVelocity.getValueAsDouble());

        inputs.appliedVolts = appliedVolts.getValueAsDouble();
        inputs.currentAmps = currentAmps.getValueAsDouble();
    }

    @Override
    public void setPosition(double position) {
        falcon.setControl(new PositionVoltage(position));
    }

    @Override 
    public void setVelocity(double veloicty) {
        falcon.setControl(new VelocityVoltage(veloicty));
    }

    @Override
    public void stop() {
        falcon.stopMotor();
    }

    @Override
    public void configurePID(double kP, double kI, double kD) {
        var config = new Slot0Configs();

        config.kP = kP;
        config.kI = kI;
        config.kD = kD;

        falcon.getConfigurator().apply(config);
    }
}
