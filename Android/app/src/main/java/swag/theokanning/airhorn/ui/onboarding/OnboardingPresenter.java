package swag.theokanning.airhorn.ui.onboarding;

import swag.theokanning.airhorn.ui.base.mvp.BasePresenter;


public class OnboardingPresenter extends BasePresenter<OnboardingView> {

    public OnboardingPresenter() {

    }

    public void onCreate() {
        getView().checkLocationPermission();
    }

    public void onLocationPermissionResult(boolean granted) {
        if (granted) {
            if (getView().isBluetoothOn()) {
                getView().startScan();
            } else {
                getView().requestBluetooth();
            }
        } else {
            getView().showLocationRationale();
        }
    }

    public void onBluetoothRequestResult(boolean enabled) {
        if(enabled){
            getView().startScan();
        } else {
            getView().showBluetoothRationale();
        }
    }
}
