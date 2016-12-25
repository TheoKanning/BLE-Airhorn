package swag.theokanning.airhorn.ui.onboarding;

import swag.theokanning.airhorn.ui.base.mvp.BasePresenter;
import timber.log.Timber;


public class OnboardingPresenter extends BasePresenter<OnboardingView> {

    public OnboardingPresenter() {

    }

    public void onCreate(){
        getView().checkLocationPermission();
    }


    public void onLocationPermissionResult(boolean granted){
        Timber.d("Permissions granted: %b", granted);
        if(granted){
            getView().isBluetoothOn();
        } else {
            getView().showLocationRationale();
        }
    }
}
