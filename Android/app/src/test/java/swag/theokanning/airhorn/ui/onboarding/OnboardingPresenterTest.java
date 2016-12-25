package swag.theokanning.airhorn.ui.onboarding;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;

public class OnboardingPresenterTest {

    @Test
    public void checksLocationPermissionInOnCreate(){
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onCreate();
        Mockito.verify(view, times(1)).checkLocationPermission();
    }

    @Test
    public void checksBluetoothIfLocationPermissionGranted(){
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(true);
        Mockito.verify(view, times(1)).isBluetoothOn();
    }

    @Test
    public void showsRationaleIfLocationPermissionRejected(){
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(false);
        Mockito.verify(view, times(1)).showLocationRationale();
    }
}
