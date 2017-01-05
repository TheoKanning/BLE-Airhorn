package swag.theokanning.airhorn.ui.fragment;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import swag.theokanning.airhorn.AirhornApplication;
import swag.theokanning.airhorn.R;
import swag.theokanning.airhorn.bluetooth.AirhornScanner;
import swag.theokanning.airhorn.service.AirhornService;
import swag.theokanning.airhorn.ui.base.BaseFragment;
import timber.log.Timber;

/**
 * Fragment that tells users that the app is working and ready
 */
public class ScanFragment extends BaseFragment {

    @Inject AirhornScanner airhornScanner;

    boolean bluetoothServiceBound = false;
    private AirhornService bluetoothService;
    private ServiceConnection airhornServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Timber.d("Bound to AirhornService");
            AirhornService.AirhornServiceBinder binder = (AirhornService.AirhornServiceBinder) service;
            bluetoothService = binder.getService();
            bluetoothServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bluetoothServiceBound = false;
            bluetoothService = null;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AirhornApplication) getContext().getApplicationContext()).getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        AirhornService.bindToService(getContext(), airhornServiceConnection);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(airhornServiceConnection != null){
            getActivity().unbindService(airhornServiceConnection);
        }
    }

}
