package swag.theokanning.airhorn.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import swag.theokanning.airhorn.bluetooth.BluetoothConnection;


public class BluetoothConnectionService extends Service {

    private BluetoothConnection.BluetoothConnectionListener listener;

    private BluetoothConnection connection;

    private BluetoothServiceBinder binder = new BluetoothServiceBinder();

    public BluetoothConnectionService() {
    }

    public static void bindToService(Context context, ServiceConnection connection) {
        Intent intent = new Intent(context, BluetoothConnectionService.class);
        context.bindService(intent, connection, Context.BIND_ABOVE_CLIENT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disconnect();
    }

    public void connectToDevice(BluetoothDevice device, BluetoothConnection.BluetoothConnectionListener listener) {
        this.listener = listener;
        this.connection = new BluetoothConnection(device, getApplicationContext(), listener);
    }

    public void disconnect() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
            listener.onDisconnect();
        }
    }

    public boolean isConnected() {
        return (connection != null && connection.isConnected());
    }

    public void sendOnCommand() {
        if (connection != null) {
            connection.sendOnCommand();
        }
    }

    public void sendOffCommand() {
        if (connection != null) {
            connection.sendOffCommand();
        }
    }

    public class BluetoothServiceBinder extends Binder {
        public BluetoothConnectionService getService() {
            return BluetoothConnectionService.this;
        }
    }
}
