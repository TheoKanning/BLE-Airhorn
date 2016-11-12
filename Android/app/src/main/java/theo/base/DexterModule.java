package theo.base;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DexterModule {

    Context context;

    public DexterModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    BluetoothScanner provideBluetoothScanner() {
        return new BluetoothScanner(context);
    }

}
