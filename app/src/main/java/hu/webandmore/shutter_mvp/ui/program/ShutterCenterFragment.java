package hu.webandmore.shutter_mvp.ui.program;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;

public class ShutterCenterFragment extends Fragment implements ShutterCenterScreen {

    private static String TAG = "ShutterCenterFragment";

    @BindView(R.id.ungroupped_shutters_list)
    RecyclerView recyclerView;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    @BindView(R.id.shutter_center_progress)
    ProgressBar progressBar;

    ArrayList<Channel> channels;
    private LinearLayoutManager llmShutters;

    ShutterCenterPresenter shutterCenterPresenter;

    public ShutterCenterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_center, container, false);

        shutterCenterPresenter = new ShutterCenterPresenter(getContext());
        shutterCenterPresenter.attachScreen(this);

        ButterKnife.bind(this, view);
        llmShutters = new LinearLayoutManager(getContext());
        llmShutters.setOrientation(LinearLayoutManager.VERTICAL);
        channels = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        shutterCenterPresenter.getShutters();
    }

    @Override
    public void onDestroyView() {
        shutterCenterPresenter.detachScreen();
        super.onDestroyView();
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void getShutters() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showShutters(ArrayList<Channel> shutters) {
        Log.i(TAG, "List size: " + String.valueOf(shutters.size()));
    }
}
