package swag.theokanning.airhorn;


import android.app.Application;

import swag.theokanning.airhorn.dagger.AirhornComponent;
import swag.theokanning.airhorn.dagger.BluetoothModule;
import swag.theokanning.airhorn.dagger.DaggerAirhornComponent;
import timber.log.Timber;

public class AirhornApplication extends Application {

    private AirhornComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAirhornComponent.builder()
                .bluetoothModule(new BluetoothModule(getApplicationContext()))
                .build();
        component.inject(this);

        Timber.plant(new Timber.DebugTree());
    }

    public AirhornComponent getComponent() {
        return component;
    }
}
