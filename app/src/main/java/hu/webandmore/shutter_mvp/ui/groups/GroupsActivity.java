package hu.webandmore.shutter_mvp.ui.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.GroupsAdapter;
import hu.webandmore.shutter_mvp.api.model.Group;

public class GroupsActivity extends AppCompatActivity implements GroupsScreen {

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
    public void showGroups(ArrayList<Group> groups) {
        groupsAdapter = new GroupsAdapter(this, groups);
        mGroupsRecyclerView.setLayoutManager(llmGroups);
        mGroupsRecyclerView.setAdapter(groupsAdapter);
    }

}
