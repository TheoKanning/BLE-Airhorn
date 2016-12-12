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

/**
 * Class for handling connection to a single device
 * Largely taken from google documentation
 * http://developer.android.com/guide/topics/connectivity/bluetooth.html
 *
 * @author Theo Kanning
 */
public class BluetoothConnection {

    public interface BluetoothConnectionListener {
        void onMessageReceived(String message);

        void onConnect();

        void onDisconnect();
    }

    private static final String TAG = BluetoothConnection.class.getSimpleName();

    private BluetoothConnectionListener bluetoothConnectionListener;

    private BluetoothDevice bluetoothDevice;

    private BluetoothGatt bluetoothGatt;

    private BluetoothGattService ledService;

    private BluetoothGattCharacteristic ledCharacteristic;

    private BluetoothGattDescriptor ledDescriptor;

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    bluetoothConnectionListener.onConnect();
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    bluetoothConnectionListener.onDisconnect();
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> services = gatt.getServices();
            Log.i("onServicesDiscovered", services.toString());
            for (BluetoothGattService service: services){
                Log.i(TAG, "Service discovered, UUID = " + service.getUuid());
            }

            ledService = gatt.getService(UUIDs.AIRHORN_SERVICE_UUID);
            if(ledService != null){
                ledCharacteristic = ledService.getCharacteristic(UUIDs.VOLUME_CHARACTERISTIC_UUID);
                ledDescriptor = ledCharacteristic.getDescriptors().get(0);
                gatt.readCharacteristic(ledCharacteristic);
                Log.d(TAG, "Characteristic discovered");
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d(TAG, "Led characteristic value: " + characteristic.getStringValue(0));
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d(TAG, "Led characteristic value: " + characteristic.getStringValue(0));
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d(TAG, "Led descriptor value: " + descriptor.getValue().toString());
        }
    };

    public BluetoothConnection(BluetoothDevice device, Context context, BluetoothConnectionListener listener) {
        this.bluetoothDevice = device;
        this.bluetoothConnectionListener = listener;
        this.bluetoothGatt = bluetoothDevice.connectGatt(context, true, gattCallback);
        this.bluetoothGatt.connect();
        this.bluetoothGatt.discoverServices();
    }

    public void sendOffCommand(){
        write(new byte[]{0x00});
    }

    public void sendOnCommand(){
        write(new byte[]{0x20});
    }

    private void write(byte[] command) {
        if(ledCharacteristic != null){
            ledCharacteristic.setValue(command);
            boolean success = bluetoothGatt.writeCharacteristic(ledCharacteristic);
            Log.d(TAG, "Wrote to led characteristic with success=" + success);
            Log.d(TAG, ledCharacteristic.getStringValue(0));
        }

//        if(ledDescriptor != null){
//            ledDescriptor.setValue(new byte[]{0x41, 0x14});
//            boolean success = bluetoothGatt.writeDescriptor(ledDescriptor);
//            byte[] bytes = ledDescriptor.getValue();
//            Log.d(TAG, "Set descriptor: " + bytes);
//            Log.d(TAG, "Wrote to led descriptor with success=" + success);
//        }
    }

    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            bluetoothGatt.close();
            bluetoothGatt = null;
        }
        ledCharacteristic = null;
        ledService = null;
    }

    public boolean isConnected() {
        return bluetoothGatt != null;
    }

}
