package swag.theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import swag.theokanning.airhorn.service.AirhornConnectionService;
import swag.theokanning.airhorn.ui.fragment.WelcomeFragment;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        BluetoothModule.class,
        AudioModule.class})
public interface AirhornComponent {
    // activities

    // application
    void inject(Application application);

    // fragments
    void inject(WelcomeFragment fragment);

    // service
    void inject(AirhornConnectionService service);
}
