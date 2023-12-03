package frc.robot.subsystems.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class intakeDeployIO implements SolenoidIO {
    private final DoubleSolenoid dSolenoid;

    public intakeDeployIO (int module, int fChannel, int rChannel) {
        dSolenoid = new DoubleSolenoid(module,PneumaticsModuleType.CTREPCM, fChannel, rChannel);
    }

    @Override
    public void updateInputs(SolenoidIOInputs inputs) {
        inputs.val = dSolenoid.get();
    }

    @Override
    public void set(Value value) {
        dSolenoid.set(value);
    }
}
