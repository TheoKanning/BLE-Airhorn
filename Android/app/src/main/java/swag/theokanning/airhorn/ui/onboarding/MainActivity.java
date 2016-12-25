package swag.theokanning.airhorn.ui.onboarding;

import android.Manifest;
import android.os.Bundle;

import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements OnboardingView {

    private static final int BLUETOOTH_AND_COARSE_LOCATION_REQUEST = 47;

    private static final int SETTINGS_REQUEST = 32;

    @Inject
    OnboardingPresenter presenter;

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AirhornApplication) getApplicationContext()).getComponent().inject(this);

        rxPermissions = new RxPermissions(this);

        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    public boolean isBluetoothOn() {
        return false;
    }

    @Override
    public void checkLocationPermission() {
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    presenter.onLocationPermissionResult(granted);
                });
    }

    @Override
    public void showLocationRationale() {

    }
}
