package swag.theokanning.airhorn.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.util.List;

import timber.log.Timber;

/**
 * Class for handling connection to a single device
 * Largely taken from google documentation
 * http://developer.android.com/guide/topics/connectivity/bluetooth.html
 *
 * @author Theo Kanning
 */
public class AirhornConnection {

    private AirhornConnectionListener airhornConnectionListener;

    private BluetoothDevice bluetoothDevice;

    private BluetoothGatt bluetoothGatt;

    private BluetoothGattService volumeService;

    private BluetoothGattCharacteristic volumeCharacteristic;

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Timber.d("STATE_CONNECTED");
                    airhornConnectionListener.onConnect();
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Timber.d("STATE_DISCONNECTED");
                    airhornConnectionListener.onDisconnect();
                    break;
                default:
                    Timber.d("STATE_OTHER");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            Timber.d("onServicesDiscovered" + services.toString());

            volumeService = gatt.getService(UUIDs.AIRHORN_SERVICE_UUID);
            if(volumeService != null){
                volumeCharacteristic = volumeService.getCharacteristic(UUIDs.VOLUME_CHARACTERISTIC_UUID);
                gatt.setCharacteristicNotification(volumeCharacteristic, true);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            airhornConnectionListener.onVolumeChanged(characteristic.getValue());
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }
    };

    public AirhornConnection(BluetoothDevice device, Context context, AirhornConnectionListener listener) {
        this.bluetoothDevice = device;
        this.airhornConnectionListener = listener;
        this.bluetoothGatt = bluetoothDevice.connectGatt(context, true, gattCallback);
        this.bluetoothGatt.connect();
        this.bluetoothGatt.discoverServices();
    }

    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
            bluetoothGatt = null;
        }
        volumeCharacteristic = null;
        volumeService = null;
    }

    public boolean isConnected() {
        return bluetoothGatt != null;
    }

}
