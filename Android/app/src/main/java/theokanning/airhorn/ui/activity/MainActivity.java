package theokanning.airhorn.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import theo.airhorn.R;
import theokanning.airhorn.ui.fragment.WelcomeFragment;

public class MainActivity extends BaseActivity {

    private static final int BLUETOOTH_REQUEST_ID = 47;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // todo turn on bluetooth if necessary
        if (hasBluetoothPermission()) {
            showWelcomeFragment();
        } else if (hasDeniedBluetoothPermission()) {
            showRationaleFragment();
        } else {
            // todo detect if the user has already denied bluetooth
            requestBluetoothPermission();
        }
    }

    private boolean hasBluetoothPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Returns true if the user has denied bluetooth and said not to ask again
     */
    private boolean hasDeniedBluetoothPermission() {
        // todo implement this
        return false;
    }

    private void requestBluetoothPermission() {
        String[] permissions = new String[]{
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH
        };
        ActivityCompat.requestPermissions(this, permissions, BLUETOOTH_REQUEST_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == BLUETOOTH_REQUEST_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showWelcomeFragment();
            }
        }
    }

    /**
     * Shows a welcome message after the user has granted bluetooth permission
     */
    private void showWelcomeFragment() {
        setFragment(new WelcomeFragment(), false);
    }

    /**
     * Shows user a fragment that explains why the bluetooth permission is necessary
     */
    private void showRationaleFragment() {
        // todo implement this
    }
}
