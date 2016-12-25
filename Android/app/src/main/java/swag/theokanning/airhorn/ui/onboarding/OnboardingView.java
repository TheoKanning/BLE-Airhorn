package swag.theokanning.airhorn.ui.onboarding;

import swag.theokanning.airhorn.ui.base.mvp.BaseView;

public interface OnboardingView extends BaseView {

    boolean isBluetoothOn();
    void checkLocationPermission();

    void showLocationRationale();
}
