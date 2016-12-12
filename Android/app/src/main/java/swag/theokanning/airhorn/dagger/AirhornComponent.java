package swag.theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import swag.theokanning.airhorn.service.BluetoothConnectionService;
import swag.theokanning.airhorn.ui.fragment.WelcomeFragment;

@Singleton
@Component(modules = BluetoothModule.class)
public interface AirhornComponent {
    // activities

    // application
    void inject(Application application);

    // fragments
    void inject(WelcomeFragment fragment);

    // service
    void inject(BluetoothConnectionService service);
}
