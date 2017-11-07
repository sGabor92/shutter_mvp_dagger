package hu.webandmore.shutter_mvp.ui.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.ShutterAdapter;
import hu.webandmore.shutter_mvp.api.model.Channel;

public class ManageChannelsActivity extends AppCompatActivity implements ManageChannelsScreen {

    private static String TAG = "ManageChannelActiviy";

    @BindView(R.id.saved_shutters_list)
    RecyclerView savedShutters;

    ArrayList<Channel> channels;
    ShutterAdapter shutterAdapter;
    private LinearLayoutManager llmShutters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_channel);

        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.copy_channel)
    public void login(View view) {
        Log.i(TAG, "Clicked on add new channel!");
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showShutters(ArrayList<Channel> shutters) {

    }

    @Override
    public void activateShutters() {

    }
}
