package swag.theokanning.airhorn.dagger;


import android.app.Application;
import android.media.SoundPool;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.sound.AirhornPlayer;

@Module
public class AudioModule {

    @Singleton
    @Provides
    SoundPool provideSoundPool(){
        return new SoundPool.Builder()
                .setMaxStreams(10)
                .build();
    }

    @Singleton
    @Provides
    AirhornPlayer provideAirhornPlayer(SoundPool soundPool, Application application){
        return new AirhornPlayer(soundPool, application);
    }
}
