package swag.theokanning.airhorn.dagger;


import android.app.Application;
import android.media.MediaPlayer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.R;

@Module
public class AudioModule {

    @Singleton
    @Provides
    MediaPlayer provideMediaPlayer(Application application){
        MediaPlayer mediaPlayer = MediaPlayer.create(application, R.raw.air_horn);
        mediaPlayer.setLooping(true);
        return mediaPlayer;
    }
}
