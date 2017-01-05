package swag.theokanning.airhorn.sound;

import android.content.Context;
import android.media.SoundPool;

import swag.theokanning.airhorn.R;

/**
 * Class that wraps {@link SoundPool} to play only airhorn sounds
 */
public class AirhornPlayer {

    private static final float VOLUME = 1;
    private static final int PRIORITY = 100;
    private static final int LOOP = 0;
    private static final float RATE = 1;

    private SoundPool soundPool;
    private int airhornSoundId;

    public AirhornPlayer(SoundPool soundPool, Context context) {
        this.soundPool = soundPool;
        this.airhornSoundId = soundPool.load(context, R.raw.air_horn, 1);
    }

    public void playAirhorn(){
        soundPool.play(airhornSoundId, VOLUME, VOLUME, PRIORITY, LOOP, RATE);
    }
}
