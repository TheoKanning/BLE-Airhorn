package swag.theokanning.airhorn;

import org.junit.Test;

import swag.theokanning.airhorn.model.AirhornCommand;

import static org.junit.Assert.*;

public class AirhornCommandTest {

    @Test
    public void volumeInRange_returnsCorrectFraction() {
        int volumeInput = 50;
        AirhornCommand command = new AirhornCommand(volumeInput);
        assertEquals(0.5f, command.getVolume(), 0.001);
        assertTrue(command.isEnabled());
    }

    @Test
    public void volumeBelowRange_returnsZero() {
        int volumeInput = -1;
        AirhornCommand command = new AirhornCommand(volumeInput);
        assertEquals(0, command.getVolume(), 0.001);
        assertFalse(command.isEnabled());
    }

    @Test
    public void volumeAboveRange_returnsOne() {
        int volumeInput = 150;
        AirhornCommand command = new AirhornCommand(volumeInput);
        assertEquals(1, command.getVolume(), 0.001);
        assertTrue(command.isEnabled());
    }

    @Test
    public void volumeBelowThreshold_notEnabled(){
        int volumeInput = 1;
        AirhornCommand command = new AirhornCommand(volumeInput);
        assertFalse(command.isEnabled());
    }
}