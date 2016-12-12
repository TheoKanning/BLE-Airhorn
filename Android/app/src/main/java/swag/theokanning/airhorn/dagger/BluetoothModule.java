package swag.theokanning.airhorn.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;

@Module
public class BluetoothModule {

    private Context context;

    public BluetoothModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    AirhornScanner provideBluetoothScanner() {
        return new AirhornScanner(context);
    }

}
