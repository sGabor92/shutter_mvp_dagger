package hu.webandmore.shutter_mvp.ui.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.GroupsAdapter;
import hu.webandmore.shutter_mvp.api.model.Group;

public class GroupsActivity extends AppCompatActivity implements GroupsScreen {

    public static final String TAG = "GroupsActivity";

    @BindView(R.id.groups_list)
    RecyclerView mGroupsRecyclerView;

    ArrayList<Group> groups;
    GroupsAdapter groupsAdapter;
    private LinearLayoutManager llmGroups;

    private GroupsPresenter groupsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupsPresenter = new GroupsPresenter(this);
        groupsPresenter.attachScreen(this);

        llmGroups = new LinearLayoutManager(this);
        llmGroups.setOrientation(LinearLayoutManager.VERTICAL);
        groups = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        groupsPresenter.getGroups();
    }

    @OnClick(R.id.addGroupBtn)
    public void addNewGroup() {
        Log.i(TAG, "Add new group!");
        groupsPresenter.addNewGroupDialog();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGroups(ArrayList<Group> groups) {
        groupsAdapter = new GroupsAdapter(this, groups);
        mGroupsRecyclerView.setLayoutManager(llmGroups);
        mGroupsRecyclerView.setAdapter(groupsAdapter);
        groupsPresenter.initSwipe();
    }

    @Override
    public void savedSuccessful() {
        groupsPresenter.getGroups();
        Toast.makeText(this, R.string.group_is_created, Toast.LENGTH_SHORT).show();
    }

    @Override
    public RecyclerView getGroupRecyclerView() {
        if (mGroupsRecyclerView != null) {
            return mGroupsRecyclerView;
        }
        return null;
    }

    @Override
    public void removeGroup(int position) {
        groupsAdapter.removeItem(position);
    }

    @Override
    public void restoreGroup(int position) {
        Group removedGroup = groupsAdapter.getItem(position);
        groupsAdapter.removeItem(position);
        groupsAdapter.restoreItem(position, removedGroup);
    }

    @Override
    public int getSelectedGroupId(int position) {
        return groupsAdapter.getCurrentId(position);
    }

}
