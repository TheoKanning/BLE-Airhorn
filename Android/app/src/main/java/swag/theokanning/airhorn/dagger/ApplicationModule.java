package swag.theokanning.airhorn.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import swag.theokanning.airhorn.ui.onboarding.OnboardingPresenter;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    OnboardingPresenter provideOnboardingPresenter(){
        return new OnboardingPresenter();
    }
}
