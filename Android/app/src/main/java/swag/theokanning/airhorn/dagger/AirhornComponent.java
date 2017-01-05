package swag.theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import swag.theokanning.airhorn.service.AirhornService;
import swag.theokanning.airhorn.ui.fragment.ScanFragment;
import swag.theokanning.airhorn.ui.onboarding.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        BluetoothModule.class,
        AudioModule.class})
public interface AirhornComponent {
    // activities
    void inject(MainActivity activity);

    // application
    void inject(Application application);

    // fragments
    void inject(ScanFragment fragment);

    // service
    void inject(AirhornService service);
}
