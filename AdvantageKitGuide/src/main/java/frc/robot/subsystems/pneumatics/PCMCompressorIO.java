package frc.robot.subsystems.pneumatics;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.subsystems.pneumatics.CompressorIO.CompressorIOInputs;

public class PCMCompressorIO implements CompressorIO{
    private final Compressor compressor;


    public PCMCompressorIO(int module) {
        compressor  = new Compressor(module, PneumaticsModuleType.CTREPCM);
    }

    @Override
    public void updateInputs(CompressorIOInputs inputs) {
        inputs.isOn = compressor.isEnabled();
        inputs.compressorCurrent = compressor.getCurrent();
        inputs.compressorPressure = compressor.getPressure();
    }

    @Override
    public void enableCompressor() {
        compressor.enableDigital();
    }

    @Override 
    public void disableCompressor() {
        compressor.disable();
    }
}
