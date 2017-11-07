package hu.webandmore.shutter_mvp.ui.nsd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.InetAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.MainActivity;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.services.NsdDeviceListener;
import hu.webandmore.shutter_mvp.api.services.NsdService;

public class SearchingDeviceActivity extends AppCompatActivity
        implements SearchingDeviceScreen, NsdDeviceListener {

    private static String TAG = "NSDSearching";
    private NsdService mNsdService;
    private InetAddress address;

    @BindView(R.id.searchingText)
    TextView searchingText;

    @BindView(R.id.deviceNotFoundLayout)
    RelativeLayout deviceNotFoundLayout;

    @BindView(R.id.searching_progress)
    ProgressBar searchingProgress;

    SearchingDevicePresenter searchingDevicePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_device);

        ButterKnife.bind(this);

        mNsdService = new NsdService(this);
        mNsdService.initializeNsd();
        mNsdService.setListener(this);

        searchingDevicePresenter = new SearchingDevicePresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNsdService != null) {
            Log.i(TAG, "Discover service");
            mNsdService.discoverServices();
        }
    }

    @Override
    protected void onPause() {
        if (mNsdService != null) {
            try {
                mNsdService.tearDown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        super.onPause();
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
    protected void onDestroy() {
        try {
            mNsdService.tearDown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.onDestroy();
    }

    @OnClick(R.id.retrySearchingBtn)
    public void retrySearching(View view) {
        searchDevice();
    }

    @Override
    public void searchDevice() {
        showSearchingProgress();
        if(mNsdService != null)
            mNsdService.discoverServices();

        searchingText.setVisibility(View.VISIBLE);
        deviceNotFoundLayout.setVisibility(View.GONE);
    }

    @Override
    public void showSearchingProgress() {
        searchingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchingProgress() {
        searchingProgress.setVisibility(View.GONE);
    }

    @Override
    public void foundDevice(InetAddress address) {
        hideSearchingProgress();
        if (address != null) {
            if (this.address == null || !this.address.equals(address)) {
                Log.i(TAG, "Device Found: " + address.toString());
                this.address = address;
                // TODO - save user device to user data
                // TODO - átgondolni, hogy mikor kell az eszközt keresni
                searchingDevicePresenter.saveDevice(address.toString());
                mNsdService.stopDiscovery();
                searchingDevicePresenter.setServiceGeneratorAddress(address.getHostAddress());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void deviceNotFound() {
        // TODO - ekkor az address nem a redonyinnyo local, hanem a test.webandmore.hu
        hideSearchingProgress();
        Log.i(TAG, "NSD SERVICE NOT FOUND");
        searchingText.setVisibility(View.GONE);
        deviceNotFoundLayout.setVisibility(View.VISIBLE);
    }
}
