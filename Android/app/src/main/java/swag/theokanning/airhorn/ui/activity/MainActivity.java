package swag.theokanning.airhorn.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.ui.fragment.WelcomeFragment;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private static final int BLUETOOTH_AND_COARSE_LOCATION_REQUEST = 47;

    private static final int SETTINGS_REQUEST = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        askForPermissions();
    }

    @AfterPermissionGranted(BLUETOOTH_AND_COARSE_LOCATION_REQUEST)
    private void askForPermissions() {
        String[] perms = {Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            showWelcomeFragment();
        } else if (EasyPermissions.somePermissionPermanentlyDenied(this, Arrays.asList(perms))) {
            // Already denied permission, show rationale
            new AppSettingsDialog.Builder(this, getString(R.string.bluetooth_and_location_permission_rationale))
                    .setTitle(getString(R.string.bluetooth_and_location_permission_title))
                    .setPositiveButton(getString(R.string.permissions_go_to_settings))
                    .setNegativeButton(getString(R.string.permissions_cancel), null /* click listener */)
                    .setRequestCode(SETTINGS_REQUEST)
                    .build()
                    .show();
        } else {
            // Do not have permissions, request them now
            String rationale = getString(R.string.bluetooth_and_location_permission_rationale);
            EasyPermissions.requestPermissions(this, rationale,
                    BLUETOOTH_AND_COARSE_LOCATION_REQUEST, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * Shows a welcome message after the user has granted bluetooth permission
     */
    private void showWelcomeFragment() {
        setFragment(new WelcomeFragment(), false);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Timber.d("Permissions granted: " + perms.toString());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Timber.d("Permissions denied: " + perms.toString());
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this, getString(R.string.bluetooth_and_location_permission_rationale))
//                    .setTitle(getString(R.string.bluetooth_and_location_permission_title))
//                    .setPositiveButton(getString(R.string.permissions_go_to_settings))
//                    .setNegativeButton(getString(R.string.permissions_cancel), null /* click listener */)
//                    .setRequestCode(SETTINGS_REQUEST)
//                    .build()
//                    .show();
//        }
    }
}
