package swag.theokanning.airhorn.ui.onboarding;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.ui.base.BaseActivity;
import swag.theokanning.airhorn.ui.fragment.ScanFragment;

public class MainActivity extends BaseActivity implements OnboardingView {

    private static final int ENABLE_BLUETOOTH_REQUEST_CODE = 47;

    @Inject OnboardingPresenter presenter;

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

    @Override
    public void showBluetoothRationale() {

    }

    @Override
    public void requestBluetooth() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, ENABLE_BLUETOOTH_REQUEST_CODE);
    }

    @Override
    public void startScan() {
        setFragment(new ScanFragment(), true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH_REQUEST_CODE) {
            presenter.onBluetoothRequestResult(resultCode == Activity.RESULT_OK);
        }
    }
}
