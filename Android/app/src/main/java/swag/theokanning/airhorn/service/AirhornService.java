package swag.theokanning.airhorn.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.bluetooth.AirhornConnection;
import swag.theokanning.airhorn.bluetooth.AirhornConnectionListener;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;
import swag.theokanning.airhorn.sound.AirhornPlayer;
import timber.log.Timber;


public class AirhornService extends Service {

    @Inject AirhornScanner airhornScanner;
    @Inject AirhornPlayer airhornPlayer;

    private AirhornServiceBinder binder = new AirhornServiceBinder();
    private List<AirhornConnection> airhornConnections = new ArrayList<>();

    private AirhornConnectionListener listener = new AirhornConnectionListener() {
        @Override
        public void onSwagCountChanged(int swagCount) {
            Timber.d("Swag count changed: %d", swagCount);
            airhornPlayer.playAirhorn();
        }

        @Override
        public void onConnect() {

        }

        @Override
        public void onDisconnect() {
        }
    };

    public static void bindToService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, AirhornService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Starting AirhornService");
        ((AirhornApplication) getApplicationContext()).getComponent().inject(this);

        if (airhornScanner.isEnabled()) {
            startAirhornScan();
        } else {
            //todo handle bluetooth not enabled
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // todo disconnect from all airhorns
    }

    private void startAirhornScan() {
        Timber.d("Starting airhorn scan");
        airhornScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                BluetoothDevice airhorn = result.getDevice();
                Timber.d("Found device: " + airhorn.getName());
                Toast.makeText(getApplicationContext(), "Scan result: " + airhorn.getName(), Toast.LENGTH_SHORT).show();
                connectToAirhorn(airhorn);
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Toast.makeText(getApplicationContext(), "Scan failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void connectToAirhorn(BluetoothDevice airhorn) {
        AirhornConnection connection = new AirhornConnection(airhorn, getApplicationContext(), listener);
        // todo check if this is the proper way to maintain multiple connections (it's probably not)
        airhornConnections.add(connection);
    }

    public class AirhornServiceBinder extends Binder {
        public AirhornService getService() {
            return AirhornService.this;
        }
    }
}
