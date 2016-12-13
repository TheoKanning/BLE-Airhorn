package swag.theokanning.airhorn.model;

/**
 * Data class to hold current airhorn state specified by peripheral
 */
public final class AirhornCommand {
    // minimum volume to be considered enabled
    private static final float ENABLED_THRESHOLD = 0.05f;

    private static final int MAX_VOLUME_INPUT = 100;

    private final float volume;

    public AirhornCommand(int volumeInput) {

        // clamp between 0 and MAX_VOLUME_INPUT
        volumeInput = Math.max(0, Math.min(volumeInput, MAX_VOLUME_INPUT));

        this.volume = ((float) volumeInput) / MAX_VOLUME_INPUT;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isEnabled() {
        return volume > ENABLED_THRESHOLD;
    }
}
