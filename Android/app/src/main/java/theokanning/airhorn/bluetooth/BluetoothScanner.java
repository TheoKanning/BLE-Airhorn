package theokanning.airhorn.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to handle all bluetooth scanning
 *
 * @author Theo Kanning
 */
public class BluetoothScanner {

    private static final String TAG = BluetoothScanner.class.getSimpleName();

    private Context context;

    private BluetoothAdapter btAdapter;
    private BluetoothLeScanner bleScanner;
    private ScanCallback scanCallback;

    private boolean mScanning;
    private Handler handler;

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    public BluetoothScanner(Context context) {
        this.context = context;
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        bleScanner = btAdapter.getBluetoothLeScanner();
        handler = new Handler();
    }

    public void startScan(final ScanCallback scanCallback) {
        this.scanCallback = scanCallback;
        Toast.makeText(context, "Scan started", Toast.LENGTH_SHORT).show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScanning = false;
                bleScanner.stopScan(scanCallback);
            }
        }, SCAN_PERIOD);

        mScanning = true;
        bleScanner.startScan(getScanFilter(), getScanSettings(), scanCallback);
    }

    public void stopScan() {
        mScanning = false;
        bleScanner.stopScan(scanCallback);
    }

    public BluetoothDevice getDevice(String address){
        return btAdapter.getRemoteDevice(address);
    }

    private List<ScanFilter> getScanFilter(){
        List<ScanFilter> filters = new ArrayList<>();
        ScanFilter.Builder builder = new ScanFilter.Builder();
        //builder.setServiceUuid(new ParcelUuid(UUIDs.AIRHORN_SERVICE_UUID));
        ScanFilter ledFilter = builder.build();

        filters.add(ledFilter);
        return filters;
    }

    private ScanSettings getScanSettings(){
        ScanSettings.Builder builder = new ScanSettings.Builder();
        //builder.setMatchMode(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
        return builder.build();
    }
}

