package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
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
        tooHot();
        assertThat(getState()).isEqualTo("hBChl");
    }

    private void tooHot() {
        hw.setTemp(TOO_HOT);
        controller.tic();
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        tooCold();
        assertThat(getState()).isEqualTo("HBchl");
    }

    private void tooCold() {
        hw.setTemp(TOO_COLD);
        controller.tic();
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        wayTooHot();
        assertThat(getState()).isEqualTo("hBCHl");
    }

    private void wayTooHot() {
        hw.setTemp(WAY_TOO_HOT);
        controller.tic();
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        wayTooCold();
        assertThat(getState()).isEqualTo("HBchL");
    }

    private void wayTooCold() {
        hw.setTemp(WAY_TOO_COLD);
        controller.tic();
    }

    public String getState() {
        StringBuffer state = new StringBuffer();
        state.append(hw.heaterState() ? "H" : "h");
        state.append(hw.blowerState() ? "B" : "b");
        state.append(hw.coolerState() ? "C" : "c");
        state.append(hw.hiTempAlarm() ? "H" : "h");
        state.append(hw.loTempAlarm() ? "L" : "l");
        return state.toString();
    }

}