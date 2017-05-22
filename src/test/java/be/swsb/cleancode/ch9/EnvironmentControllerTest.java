package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
import static be.swsb.cleancode.ch9.HardwareComponents.*;
import static be.swsb.cleancode.ch9.HardwareState.OFF;
import static be.swsb.cleancode.ch9.HardwareState.ON;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
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
    public void tic_GivenTempTooHot_ThenTurnOnCoolerAndBlower() throws Exception {
        // GIVEN
        hw.setTemp(TOO_HOT);

        // WHEN
        controller.tic();

        // THEN
        assertThatComponentsAreOn(asList(BLOWER, COOLER));
    }

    @Test
    public void tic_GivenTempTooCold_ThenTurnOnHeaterAndBlower() throws Exception {
        // GIVEN
        hw.setTemp(TOO_COLD);

        // WHEN
        controller.tic();

        // THEN
        assertThatComponentsAreOn(asList(HEATER, BLOWER));
    }

    @Test
    public void tic_GivenTempWayTooHot_ThenTurnOnHiTempAlarm() throws Exception {
        // GIVEN
        hw.setTemp(WAY_TOO_HOT);

        // WHEN
        controller.tic();

        // THEN
        assertThatComponentsAreOn(asList(BLOWER, COOLER, HI_TEMP_ALARM));
    }

    @Test
    public void tic_GivenTempWayTooCold_ThenTurnOnLoTempAlarm() throws Exception {
        // GIVEN
        hw.setTemp(WAY_TOO_COLD);

        // WHEN
        controller.tic();

        // THEN
        assertThatComponentsAreOn(asList(HEATER, BLOWER, LO_TEMP_ALARM));
    }

    private void assertThatComponentsAreOn(List<HardwareComponents> hardwareComponents) {
        hardwareComponents
                .forEach(hardwareComponent -> assertThat(hw.getStateOf(hardwareComponent)).isEqualTo(ON));
        stream(HardwareComponents.values())
                .filter(hardwareComponent -> ! hardwareComponents.contains(hardwareComponent))
                .forEach(hardwareComponent -> assertThat(hw.getStateOf(hardwareComponent)).isEqualTo(OFF));
    }

}