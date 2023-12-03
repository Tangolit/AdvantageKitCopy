package frc.robot.subsystems.flywheel;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.flywheel.FlywheelIO.FlywheelIOInputs;

public class FlywheelIOTalonFX implements FlywheelIO{
    private final TalonFX leader;
    private final TalonFX follower;

    private final StatusSignal<Double> velocity;
    private final StatusSignal<Double> appliedVolts;
    private final StatusSignal<Double> mCurrentAmps;
    private final StatusSignal<Double> sCurrentAmps;

    public FlywheelIOTalonFX(int mID, int sID) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        this.leader = new TalonFX(mID);
        this.follower = new TalonFX(sID);

        follower.setControl(new Follower(leader.getDeviceID(), false));

        leader.getConfigurator().apply(config);
        follower.getConfigurator().apply(config);

        this.velocity = leader.getVelocity();
        this.appliedVolts = leader.getMotorVoltage();
        this.mCurrentAmps = leader.getStatorCurrent();
        this.sCurrentAmps = follower.getStatorCurrent();

        BaseStatusSignal.setUpdateFrequencyForAll(100, velocity, appliedVolts, mCurrentAmps, sCurrentAmps);
    }

    @Override
    public void updateInputs(FlywheelIOInputs inputs) {
        BaseStatusSignal.refreshAll(velocity, appliedVolts, mCurrentAmps, sCurrentAmps);

        inputs.velocity = Units.rotationsPerMinuteToRadiansPerSecond(velocity.getValueAsDouble());
        inputs.appliedVolts = appliedVolts.getValueAsDouble();
        inputs.currentAmps = new double[] {mCurrentAmps.getValueAsDouble(), sCurrentAmps.getValueAsDouble()};
    }

    @Override
    public void setVoltage(double voltage) {
        leader.setControl(new VoltageOut(voltage));
    }

    @Override
    public void setVelocity(double velocity) {
        leader.setControl(new VelocityVoltage(velocity));
    }

    @Override 
    public void stop() {
        leader.stopMotor();
    }

    @Override
    public void configurePID(double kP, double kI, double kD) {
        Slot0Configs config = new Slot0Configs();
        config.kP = kP;
        config.kI = kI;
        config.kD = kD;
        leader.getConfigurator().apply(config);
    }
}