package swag.theokanning.airhorn;


import android.app.Application;
import android.content.Intent;

import swag.theokanning.airhorn.dagger.AirhornComponent;
import swag.theokanning.airhorn.dagger.ApplicationModule;
import swag.theokanning.airhorn.dagger.AudioModule;
import swag.theokanning.airhorn.dagger.BluetoothModule;
import swag.theokanning.airhorn.dagger.DaggerAirhornComponent;
import swag.theokanning.airhorn.service.AirhornService;
import timber.log.Timber;

public class AirhornApplication extends Application {

    private AirhornComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAirhornComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .bluetoothModule(new BluetoothModule())
                .audioModule(new AudioModule())
                .build();
        component.inject(this);

        Timber.plant(new Timber.DebugTree());

        startService(new Intent(this, AirhornService.class));
    }

    public AirhornComponent getComponent() {
        return component;
    }
}
