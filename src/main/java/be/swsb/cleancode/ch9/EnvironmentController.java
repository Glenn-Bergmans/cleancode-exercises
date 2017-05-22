package be.swsb.cleancode.ch9;

import static be.swsb.cleancode.ch9.HardwareComponents.*;
import static be.swsb.cleancode.ch9.HardwareState.OFF;
import static be.swsb.cleancode.ch9.HardwareState.ON;

public class EnvironmentController {

    public static final double TOO_HOT = 30.0;
    public static final double TOO_COLD = 11.0;
    public static final double WAY_TOO_HOT = 34.0;
    public static final double WAY_TOO_COLD = 6.0;

    private ControlHardware controlHardware;

    public EnvironmentController(ControlHardware controlHardware) {
        this.controlHardware = controlHardware;
    }

    // Just like Uncle Bob says: I'd rather you not worry about that while reading the test.
    // I'd rather you just worry about whether you agree that the end state of the system is consistent with the temperature being "way too cold".
    public void tic() {
        double currentTemperature = controlHardware.getTemp();

        if (currentTemperature <= TOO_COLD) {
            controlHardware.setStateOf(BLOWER, ON);
            controlHardware.setStateOf(HEATER, ON);
            if (currentTemperature <= WAY_TOO_COLD) {
                controlHardware.setStateOf(LO_TEMP_ALARM, ON);
            } else {
                controlHardware.setStateOf(LO_TEMP_ALARM, OFF);
            }
        } else if (currentTemperature >= TOO_HOT) {
            controlHardware.setStateOf(BLOWER, ON);
            controlHardware.setStateOf(COOLER, ON);
            if (currentTemperature >= WAY_TOO_HOT) {
                controlHardware.setStateOf(HI_TEMP_ALARM, ON);
            } else {
                controlHardware.setStateOf(HI_TEMP_ALARM, OFF);
            }
        } else {
            controlHardware.setStateOf(BLOWER, OFF);
            controlHardware.setStateOf(COOLER, OFF);
            controlHardware.setStateOf(HEATER, OFF);
            controlHardware.setStateOf(LO_TEMP_ALARM, OFF);
            controlHardware.setStateOf(HI_TEMP_ALARM, OFF);
        }
    }
}