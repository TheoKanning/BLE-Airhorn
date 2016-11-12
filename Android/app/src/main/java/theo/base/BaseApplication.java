package theo.base;


import android.app.Application;

import theo.base.dagger.BaseComponent;
import theo.base.dagger.BaseModule;
import theo.base.dagger.DaggerBaseComponent;

public class BaseApplication extends Application {

    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerBaseComponent.builder()
                .baseModule(new BaseModule(this))
                .build();
        component.inject(this);
    }
    public BaseComponent getComponent() {
        return component;
    }
}
