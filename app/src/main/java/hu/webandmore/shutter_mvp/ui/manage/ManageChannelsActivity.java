package hu.webandmore.shutter_mvp.ui.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    @BindView(R.id.manage_progress)
    ProgressBar mProgressView;

    @BindView(R.id.mainLayout)
    LinearLayout mMainView;

    ArrayList<Channel> channels;
    ShutterAdapter shutterAdapter;
    private LinearLayoutManager llmShutters;

    private ManageChannelsPresenter manageChannelsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_channel);

        ButterKnife.bind(this);

        manageChannelsPresenter = new ManageChannelsPresenter(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llmShutters = new LinearLayoutManager(this);
        llmShutters.setOrientation(LinearLayoutManager.VERTICAL);
        channels = new ArrayList<>();

        showProgressBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        manageChannelsPresenter.getShutters();
    }

    @Override
    protected void onStart() {
        super.onStart();
        manageChannelsPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        manageChannelsPresenter.detachScreen();
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
    public void createNewChannel(View view) {
        manageChannelsPresenter.createShutter();
    }

    @Override
    public void showProgressBar() {
        mProgressView.setVisibility(View.VISIBLE);
        mMainView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        mProgressView.setVisibility(View.GONE);
        mMainView.setVisibility(View.VISIBLE);
    }

    //TODO - valamiért meghívódik törlés után is, csekkolni!
    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showShutters(ArrayList<Channel> shutters) {
        shutterAdapter = new ShutterAdapter(this, shutters);
        savedShutters.setLayoutManager(llmShutters);
        savedShutters.setAdapter(shutterAdapter);
        manageChannelsPresenter.initSwipe();
    }

    @Override
    public void activateShutters() {
        shutterAdapter.activateChannels();
    }

    @Override
    public void removeShutter(int position) {
        Log.i(TAG, "Delete shutter: " + position);
        shutterAdapter.removeItem(position);
    }

    @Override
    public void restoreShutter(int position) {
        Log.i(TAG, "Restore shutter: " + position);
        Channel removedChannel = shutterAdapter.getItem(position);
        shutterAdapter.removeItem(position);
        shutterAdapter.restoreItem(position, removedChannel);
    }

    @Override
    public RecyclerView getSavedShutters() {
        if(savedShutters != null) {
            return savedShutters;
        }
        return null;
    }

    @Override
    public int getSelectedShutterId(int position) {
        return shutterAdapter.getCurrentId(position);
    }

    @Override
    public void switchToCreatedChannel(int id) {
        Intent intent = new Intent(this, NewShutterActivity.class);
        intent.putExtra("channelID", id);
        startActivity(intent);
    }

}
