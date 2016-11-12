package theokanning.airhorn.ui.activity;

import android.os.Bundle;

import theo.airhorn.R;
import theokanning.airhorn.ui.fragment.WelcomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragment(new WelcomeFragment(), false);
    }
}
