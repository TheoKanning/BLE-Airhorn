package swag.theokanning.airhorn.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.service.AirhornConnectionService;
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
        checkPermissions();
        // todo move this into boot receiver?
        startService(new Intent(getApplicationContext(), AirhornConnectionService.class));
    }

    @AfterPermissionGranted(BLUETOOTH_AND_COARSE_LOCATION_REQUEST)
    private void checkPermissions() {
        String[] perms = {Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            showWelcomeFragment();
        } else if (EasyPermissions.somePermissionPermanentlyDenied(this, Arrays.asList(perms))) {
            showPermissionRationale();
        } else {
            requestPermissions(perms);
        }
    }

    /**
     * Shows a dialog explaining why the permissions are needed
     * todo replace this with a non-modal message
     */
    private void showPermissionRationale() {
        // Already denied permission, show rationale
        new AppSettingsDialog.Builder(this, getString(R.string.bluetooth_and_location_permission_rationale))
                .setTitle(getString(R.string.bluetooth_and_location_permission_title))
                .setPositiveButton(getString(R.string.permissions_go_to_settings))
                .setNegativeButton(getString(R.string.permissions_cancel), null /* click listener */)
                .setRequestCode(SETTINGS_REQUEST)
                .build()
                .show();
    }

    /**
     * Sends a request for the provided list of permissions
     * @param perms strings array containing permissions ids
     */
    private void requestPermissions(String[] perms) {
        // Do not have permissions, request them now
        String rationale = getString(R.string.bluetooth_and_location_permission_rationale);
        EasyPermissions.requestPermissions(this, rationale,
                BLUETOOTH_AND_COARSE_LOCATION_REQUEST, perms);
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
        // todo show message and give link to settings
    }
}
