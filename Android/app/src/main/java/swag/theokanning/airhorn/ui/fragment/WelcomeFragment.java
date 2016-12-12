package swag.theokanning.airhorn.ui.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;
import swag.theokanning.airhorn.service.AirhornConnectionService;
import timber.log.Timber;

/**
 * Fragment that tells users that the app is working and ready
 */
public class WelcomeFragment extends BaseFragment {

    private static final int ENABLE_BLUETOOTH_REQUEST_CODE = 1;

    @Inject AirhornScanner airhornScanner;

    boolean bluetoothServiceBound = false;
    private AirhornConnectionService bluetoothService;
    private ServiceConnection btServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Timber.d("Bound to AirhornConnectionService");
            AirhornConnectionService.AirhornServiceBinder binder = (AirhornConnectionService.AirhornServiceBinder) service;
            bluetoothService = binder.getService();
            bluetoothServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bluetoothServiceBound = false;
            bluetoothService = null;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AirhornApplication) getContext().getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (airhornScanner.isEnabled()) {
            startBleService();
        } else {
            enableBluetooth();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void enableBluetooth() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, ENABLE_BLUETOOTH_REQUEST_CODE);
    }

    private void startBleService() {
        AirhornConnectionService.bindToService(getContext(), btServiceConnection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                startBleService();
            }
        }
    }
}
