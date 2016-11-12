package theokanning.airhorn;


import android.app.Application;

import theokanning.airhorn.dagger.AirhornComponent;
import theokanning.airhorn.dagger.DaggerAirhornComponent;

public class AirhornApplication extends Application {

    private AirhornComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAirhornComponent.create();
        component.inject(this);
    }

    public AirhornComponent getComponent() {
        return component;
    }
}
