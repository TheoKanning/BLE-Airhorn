package theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import theokanning.airhorn.ui.fragment.WelcomeFragment;

@Singleton
@Component(modules = BluetoothModule.class)
public interface AirhornComponent {
    // activities

    // application
    void inject(Application application);

    // fragments
    void inject(WelcomeFragment fragment);
}
