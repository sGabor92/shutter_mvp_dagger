package hu.webandmore.shutter_mvp.ui.nsd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import hu.webandmore.shutter_mvp.R;

public class SearchingDeviceActivity extends AppCompatActivity implements SearchingDeviceScreen {

    //private static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_device);

        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void searchDevice() {

    }
}
