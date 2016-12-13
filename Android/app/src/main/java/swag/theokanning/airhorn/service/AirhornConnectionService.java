package swag.theokanning.airhorn.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.bluetooth.AirhornConnection;
import swag.theokanning.airhorn.bluetooth.AirhornConnectionListener;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;
import swag.theokanning.airhorn.model.AirhornCommand;
import timber.log.Timber;


public class AirhornConnectionService extends Service {

    @Inject AirhornScanner airhornScanner;

    private MediaPlayer mediaPlayer;
    private boolean playingSound = false;
    private AirhornServiceBinder binder = new AirhornServiceBinder();
    private List<AirhornConnection> airhornConnections = new ArrayList<>();
    private AirhornConnectionListener listener = new AirhornConnectionListener() {
        @Override
        public void onVolumeChanged(AirhornCommand command) {
            Timber.d("Volume changed: %f", command.getVolume());
            if (command.isEnabled()) {
                if (!playingSound) {
                    startPlaying();
                }
                mediaPlayer.setVolume(command.getVolume(), command.getVolume());
            } else if (playingSound) {
                stopPlaying();
            }
        }

        @Override
        public void onConnect() {

        }

        @Override
        public void onDisconnect() {

        }
    };

    public AirhornConnectionService() {
    }

    public static void bindToService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, AirhornConnectionService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("Starting AirhornConnectionService");
        ((AirhornApplication) getApplicationContext()).getComponent().inject(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
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

    private void startPlaying() {
        Timber.d("Starting swag");
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        playingSound = true;
    }

    private void stopPlaying() {
        Timber.d("Stopping swag");
        mediaPlayer.pause();
        playingSound = false;
    }

    public class AirhornServiceBinder extends Binder {
        public AirhornConnectionService getService() {
            return AirhornConnectionService.this;
        }
    }
}
