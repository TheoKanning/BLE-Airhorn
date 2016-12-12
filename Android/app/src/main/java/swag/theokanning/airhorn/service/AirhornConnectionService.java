package swag.theokanning.airhorn.service;

import android.app.Service;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;
import timber.log.Timber;


public class AirhornConnectionService extends Service {

    @Inject AirhornScanner airhornScanner;

    private AirhornServiceBinder binder = new AirhornServiceBinder();

    public AirhornConnectionService() {}

    public static void bindToService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, AirhornConnectionService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Starting AirhornConnectionService");
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
                Timber.d("Found device: " + result.getDevice().getName());
                Toast.makeText(getApplicationContext(), "Scan result: " + result.getDevice().getName(), Toast.LENGTH_SHORT).show();
                // todo connect to device
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

    public class AirhornServiceBinder extends Binder {
        public AirhornConnectionService getService() {
            return AirhornConnectionService.this;
        }
    }
}
