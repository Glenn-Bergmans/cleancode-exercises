package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
import static be.swsb.cleancode.ch9.HardwareComponents.*;
import static be.swsb.cleancode.ch9.HardwareState.OFF;
import static be.swsb.cleancode.ch9.HardwareState.ON;
import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentControllerTest {

    private EnvironmentController controller;
    private MockControlHardware hw;

    @Before
    public void setUp() throws Exception {
        hw = new MockControlHardware();
        controller = new EnvironmentController(hw);
    }

    @Test
    public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
        hw.setTemp(TOO_HOT);
        controller.tic();
        assertThat(hw.getStateOf(HEATER)).isEqualTo(OFF);
        assertThat(hw.getStateOf(BLOWER)).isEqualTo(ON);
        assertThat(hw.getStateOf(COOLER)).isEqualTo(ON);
        assertThat(hw.getStateOf(HI_TEMP_ALARM)).isEqualTo(OFF);
        assertThat(hw.getStateOf(LO_TEMP_ALARM)).isEqualTo(OFF);
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        hw.setTemp(TOO_COLD);
        controller.tic();
        assertThat(hw.getStateOf(HEATER)).isEqualTo(ON);
        assertThat(hw.getStateOf(BLOWER)).isEqualTo(ON);
        assertThat(hw.getStateOf(COOLER)).isEqualTo(OFF);
        assertThat(hw.getStateOf(HI_TEMP_ALARM)).isEqualTo(OFF);
        assertThat(hw.getStateOf(LO_TEMP_ALARM)).isEqualTo(OFF);
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        hw.setTemp(WAY_TOO_HOT);
        controller.tic();
        assertThat(hw.getStateOf(HEATER)).isEqualTo(OFF);
        assertThat(hw.getStateOf(BLOWER)).isEqualTo(ON);
        assertThat(hw.getStateOf(COOLER)).isEqualTo(ON);
        assertThat(hw.getStateOf(HI_TEMP_ALARM)).isEqualTo(ON);
        assertThat(hw.getStateOf(LO_TEMP_ALARM)).isEqualTo(OFF);
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        hw.setTemp(WAY_TOO_COLD);
        controller.tic();
        assertThat(hw.getStateOf(HEATER)).isEqualTo(ON);
        assertThat(hw.getStateOf(BLOWER)).isEqualTo(ON);
        assertThat(hw.getStateOf(COOLER)).isEqualTo(OFF);
        assertThat(hw.getStateOf(HI_TEMP_ALARM)).isEqualTo(OFF);
        assertThat(hw.getStateOf(LO_TEMP_ALARM)).isEqualTo(ON);
    }

}