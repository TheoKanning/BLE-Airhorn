package swag.theokanning.airhorn.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.bluetooth.BluetoothScanner;

@Module
public class BluetoothModule {

    private Context context;

    public BluetoothModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @Singleton
    BluetoothScanner provideBluetoothScanner() {
        return new BluetoothScanner(context);
    }

}
