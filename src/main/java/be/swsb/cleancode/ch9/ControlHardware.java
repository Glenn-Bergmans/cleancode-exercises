package be.swsb.cleancode.ch9;

public interface ControlHardware {
    double getTemp();
    HardwareState getStateOf(HardwareComponents hardwareComponent);
    void setStateOf(HardwareComponents hardwareComponent, HardwareState state);
}