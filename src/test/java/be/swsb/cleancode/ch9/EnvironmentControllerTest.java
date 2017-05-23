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
    private MockControlHardware controlHardware;

    @Before
    public void setUp() throws Exception {
        controlHardware = new MockControlHardware();
        controller = new EnvironmentController(controlHardware);
    }

    @Test
    public void tic_GivenTempTooHot_ThenTurnOnCoolerAndBlower() throws Exception {
        // GIVEN
        controlHardware.setTemp(TOO_HOT);

        // WHEN
        controller.tic();

        // THEN
        assertThatOnlyTheseComponentsAreOn(BLOWER, COOLER);
    }

    @Test
    public void tic_GivenTempTooCold_ThenTurnOnHeaterAndBlower() throws Exception {
        // GIVEN
        controlHardware.setTemp(TOO_COLD);

        // WHEN
        controller.tic();

        // THEN
        assertThatOnlyTheseComponentsAreOn(HEATER, BLOWER);
    }

    @Test
    public void tic_GivenTempWayTooHot_ThenTurnOnHiTempAlarm() throws Exception {
        // GIVEN
        controlHardware.setTemp(WAY_TOO_HOT);

        // WHEN
        controller.tic();

        // THEN
        assertThatOnlyTheseComponentsAreOn(BLOWER, COOLER, HI_TEMP_ALARM);
    }

    @Test
    public void tic_GivenTempWayTooCold_ThenTurnOnLoTempAlarm() throws Exception {
        // GIVEN
        controlHardware.setTemp(WAY_TOO_COLD);

        // WHEN
        controller.tic();

        // THEN
        assertThatOnlyTheseComponentsAreOn(HEATER, BLOWER, LO_TEMP_ALARM);
    }

    private void assertThatOnlyTheseComponentsAreOn(HardwareComponents... hardwareComponents) {
        List<HardwareComponents> componentsThatShouldBeOn = asList(hardwareComponents);

        componentsThatShouldBeOn
                .forEach(hardwareComponent -> assertThat(controlHardware.getStateOf(hardwareComponent)).isEqualTo(ON));

        stream(HardwareComponents.values())
                .filter(hardwareComponent -> ! componentsThatShouldBeOn.contains(hardwareComponent))
                .forEach(hardwareComponent -> assertThat(controlHardware.getStateOf(hardwareComponent)).isEqualTo(OFF));
    }

}