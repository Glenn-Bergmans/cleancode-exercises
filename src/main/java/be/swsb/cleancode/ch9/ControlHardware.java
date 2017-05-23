package be.swsb.cleancode.ch9;

import java.util.List;

public interface ControlHardware {
    double getTemp();
    HardwareState getStateOf(HardwareComponents hardwareComponent);
    void setStateOf(HardwareComponents hardwareComponent, HardwareState state);
    List<HardwareComponents> getComponents();
}