package swag.theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;

@Module
public class BluetoothModule {

    @Provides
    @Singleton
    AirhornScanner provideBluetoothScanner(Application application) {
        return new AirhornScanner(application);
    }
}
