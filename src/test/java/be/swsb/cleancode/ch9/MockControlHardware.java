package be.swsb.cleancode.ch9;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class MockControlHardware implements ControlHardware {
    private double temp;
    private Map<HardwareComponents, HardwareState> hardwareStates;

    public MockControlHardware() {
        hardwareStates = stream(HardwareComponents.values()).collect(toMap(identity(), x -> HardwareState.OFF));
    }

    void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public double getTemp() {
        return temp;
    }

    @Override
    public HardwareState getStateOf(HardwareComponents hardwareComponent) {
        return hardwareStates.get(hardwareComponent);
    }

    @Override
    public void setStateOf(HardwareComponents hardwareComponent, HardwareState state) {
        hardwareStates.put(hardwareComponent, state);
    }

}
