package hu.webandmore.shutter_mvp.ui.program;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.ui.groups.EditGroupActivity;
import hu.webandmore.shutter_mvp.ui.groups.GroupsActivity;
import hu.webandmore.shutter_mvp.ui.manage.ManageChannelsActivity;
import hu.webandmore.shutter_mvp.ui.nsd.SearchingDeviceActivity;
import hu.webandmore.shutter_mvp.utils.Utils;

public class SettingsFragment extends Fragment {

    private static String TAG = "SettingsFragment";

    @BindView(R.id.logoutUser)
    Button logoutBtn;

    @BindView(R.id.manageChannels)
    Button manageChannelsBtn;


    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shutter_settings, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.logoutUser)
    public void logoutUser() {
        Log.i(TAG, "Logout user");
        Utils.userLogout(getContext());
    }

    @OnClick(R.id.manageChannels)
    public void manageChannels() {
        Intent intent = new Intent(getContext(), ManageChannelsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.manageGroups)
    public void manageGroups() {
        Intent intent = new Intent(getContext(), GroupsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.searchDevice)
    public void searchDevice() {
        Intent intent = new Intent(getContext(), SearchingDeviceActivity.class);
        startActivity(intent);
    }

}
