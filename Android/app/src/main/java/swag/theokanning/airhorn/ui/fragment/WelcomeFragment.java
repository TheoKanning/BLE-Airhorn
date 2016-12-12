package swag.theokanning.airhorn.ui.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.bluetooth.BluetoothConnection;
import swag.theokanning.airhorn.bluetooth.BluetoothScanner;
import swag.theokanning.airhorn.service.BluetoothConnectionService;
import timber.log.Timber;

/**
 * Fragment that tells users that the app is working and ready
 */
public class WelcomeFragment extends BaseFragment {

    private static final int ENABLE_BLUETOOTH_REQUEST_CODE = 1;

    @Inject BluetoothScanner bluetoothScanner;
    boolean bluetoothServiceBound = false;
    private BluetoothConnectionService bluetoothService;
    private BluetoothDevice device;
    private ServiceConnection btServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Timber.d("Bound to BluetoothConnectionService");
            BluetoothConnectionService.BluetoothServiceBinder binder = (BluetoothConnectionService.BluetoothServiceBinder) service;
            bluetoothService = binder.getService();
            bluetoothServiceBound = true;
            bluetoothService.connectToDevice(device, new BluetoothConnection.BluetoothConnectionListener() {
                @Override
                public void onMessageReceived(String message) {

                }

                @Override
                public void onConnect() {

                }

                @Override
                public void onDisconnect() {

                }
            });
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
        if (bluetoothScanner.isEnabled()) {
            startAirhornScan();
        } else {
            enableBluetooth();
        }
    }

    private void enableBluetooth() {
        Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBluetoothIntent, ENABLE_BLUETOOTH_REQUEST_CODE);
    }

    private void startAirhornScan() {
        bluetoothScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Toast.makeText(getContext(), "Scan result: " + result.toString(), Toast.LENGTH_SHORT).show();
                device = result.getDevice();
                startBleService();
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Toast.makeText(getContext(), "Scan failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startBleService() {
        BluetoothConnectionService.bindToService(getContext(), btServiceConnection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                startAirhornScan();
            }
        }
    }
}
