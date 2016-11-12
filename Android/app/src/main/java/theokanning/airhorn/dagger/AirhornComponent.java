package theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = BluetoothModule.class)
public interface AirhornComponent {
    //activities

    //application
    void inject(Application application);

}
