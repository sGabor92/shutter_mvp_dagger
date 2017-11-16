package hu.webandmore.shutter_mvp.ui.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.GroupShutterAdapter;
import hu.webandmore.shutter_mvp.api.model.Channel;

public class ManageGroupActivity extends AppCompatActivity implements ManageGroupScreen {

    private static final String TAG = "ManageGroupActivity";

    @BindView(R.id.group_name_input)
    EditText mGroupNameInput;

    @BindView(R.id.shutters_list)
    RecyclerView shuttersRecyclerView;

    ArrayList<Channel> channels;
    GroupShutterAdapter groupShutterAdapter;
    private LinearLayoutManager llmShutters;

    ManageGroupPresenter manageGroupPresenter;

    private int groupId = 0;
    private ArrayList<Integer> mGroupChannels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                if (getIntent().hasExtra("groupId")) {
                    groupId = extras.getInt("groupId");
                }
                if (getIntent().hasExtra("groupName")) {
                    mGroupNameInput.setText(extras.getString("groupName"));
                }
                if (getIntent().hasExtra("channels")) {
                    mGroupChannels = extras.getIntegerArrayList("channels");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        manageGroupPresenter = new ManageGroupPresenter(this);
        manageGroupPresenter.attachScreen(this);

        llmShutters = new LinearLayoutManager(this);
        llmShutters.setOrientation(LinearLayoutManager.VERTICAL);
        channels = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageGroupPresenter.getShutters();
    }

    @OnClick(R.id.save_group)
    public void saveGroup() {
        if (TextUtils.isEmpty(mGroupNameInput.getText().toString())) {
            mGroupNameInput.setError(getString(R.string.required_field), null);
            mGroupNameInput.requestFocus();
        } else {
            manageGroupPresenter.modifyGroupName(groupId, mGroupNameInput.getText().toString());
        }
    }

    @OnClick(R.id.cancel_add_group)
    public void cancel() {
        finish();
    }

    @Override
    public void showShutters(ArrayList<Channel> shutters) {
        groupShutterAdapter = new GroupShutterAdapter(this, shutters, groupId, mGroupChannels);
        shuttersRecyclerView.setLayoutManager(llmShutters);
        shuttersRecyclerView.setAdapter(groupShutterAdapter);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void savedSuccessful() {
        Toast.makeText(this, R.string.group_is_modified, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void channelAttached() {
        Toast.makeText(this, R.string.channel_attached, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void channelDetached() {
        Toast.makeText(this, R.string.channel_detached, Toast.LENGTH_SHORT).show();
    }

}
