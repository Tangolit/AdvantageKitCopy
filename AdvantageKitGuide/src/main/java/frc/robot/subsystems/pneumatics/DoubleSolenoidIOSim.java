package frc.robot.subsystems.pneumatics;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.simulation.DoubleSolenoidSim;

public class DoubleSolenoidIOSim implements SolenoidIO{
    DoubleSolenoidSim dSolenoid;

    public DoubleSolenoidIOSim (int module, int fChannel, int rChannel) {
        dSolenoid = new DoubleSolenoidSim(module,PneumaticsModuleType.CTREPCM, fChannel, rChannel);
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
