package swag.theokanning.airhorn.ui.onboarding;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class OnboardingPresenterTest {

    @Test
    public void checksLocationPermissionInOnCreate() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onCreate();
        Mockito.verify(view, times(1)).checkLocationPermission();
    }

    @Test
    public void checksBluetoothIfLocationPermissionGranted() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(true);
        Mockito.verify(view, times(1)).isBluetoothOn();
    }

    @Test
    public void showsRationaleIfLocationPermissionRejected() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(false);
        Mockito.verify(view, times(1)).showLocationRationale();
    }

    @Test
    public void requestsBluetoothIfNotEnabledAfterLocation() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        when(view.isBluetoothOn()).thenReturn(false);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(true);
        Mockito.verify(view, times(1)).requestBluetooth();
    }

    @Test
    public void startScanIfBluetoothEnabledAfterLocation() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        when(view.isBluetoothOn()).thenReturn(true);
        presenter.attachView(view);

        presenter.onLocationPermissionResult(true);
        Mockito.verify(view, times(1)).startScan();
    }

    @Test
    public void startScanIfBluetoothEnabledAfterRequest() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onBluetoothRequestResult(true);
        Mockito.verify(view, times(1)).startScan();
    }

    @Test
    public void showBluetoothRationaleIfBluetoothDenied() {
        OnboardingPresenter presenter = new OnboardingPresenter();
        OnboardingView view = Mockito.mock(OnboardingView.class);
        presenter.attachView(view);

        presenter.onBluetoothRequestResult(false);
        Mockito.verify(view, times(1)).showBluetoothRationale();
    }
}
