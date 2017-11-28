package hu.webandmore.shutter_mvp.ui.automation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.DayPickerAdapter;
import hu.webandmore.shutter_mvp.adapter.GroupAutomationAdapter;
import hu.webandmore.shutter_mvp.adapter.GroupShutterAdapter;
import hu.webandmore.shutter_mvp.adapter.GroupsAdapter;
import hu.webandmore.shutter_mvp.adapter.ShutterAdapter;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.app.Enums;
import hu.webandmore.shutter_mvp.ui.program.ShutterCenterPresenter;

public class CreateAutomationAcivity extends AppCompatActivity implements CreateAutomationScreen {

    @BindView(R.id.automation_name)
    EditText mAutomationName;

    @BindView(R.id.daysRecyclerView)
    RecyclerView mDaysRecyclerView;

    @BindView(R.id.selectEveryDay)
    Button mSelectEveryDay;

    @BindView(R.id.timePicker)
    TimePicker mTimepicker;

    @BindView(R.id.timepicker_layout)
    RelativeLayout mTimepickerLayout;

    @BindView(R.id.set_time_icon)
    ImageView mSetTimeIcon;

    @BindView(R.id.sunrise_icon)
    ImageView mSunriseIcon;

    @BindView(R.id.sunset_icon)
    ImageView mSunsetIcon;

    @BindView(R.id.shutterMovement)
    ToggleButton mShutterMovement;

    @BindView(R.id.groupsLayout)
    LinearLayout mGroupsLayout;

    @BindView(R.id.selected_groups_list)
    RecyclerView mGroupsRecyclerView;

    @BindView(R.id.countText)
    TextView mCountText;

    ArrayList<PickedDay> mDays;
    DayPickerAdapter mDayPickerAdapter;
    private LinearLayoutManager llmDays;

    CreateAutomationPresenter createAutomationPresenter;

    Automation automation;

    private boolean daysSelected = false;
    private boolean isUpperwardsMovement = false;

    ArrayList<Group> groups;
    GroupAutomationAdapter groupAutomationAdapter;
    private LinearLayoutManager llmGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_automation_acivity);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        llmDays = new LinearLayoutManager(this);
        llmDays.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDays = new ArrayList<>();

        llmGroups = new LinearLayoutManager(this);
        llmGroups.setOrientation(LinearLayoutManager.VERTICAL);
        groups = new ArrayList<>();

        createAutomationPresenter = new CreateAutomationPresenter(this);

        automation = new Automation();
        automation.setAutomation_time("sunrise");

        mTimepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                automation.setAutomation_time(String.valueOf(hourOfDay) +
                        ":" + String.valueOf(minute));
            }
        });

        mShutterMovement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isUpperwardsMovement = isChecked;
            }
        });

        String countText = String.format(getResources().getString(R.string._0_room), 0);
        mCountText.setText(countText);

    }

    @Override
    protected void onStart() {
        super.onStart();
        createAutomationPresenter.attachScreen(this);
        createAutomationPresenter.fillDays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createAutomationPresenter.getGroups();
    }

    @Override
    protected void onStop() {
        super.onStop();
        createAutomationPresenter.detachScreen();
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

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showDays(ArrayList<PickedDay> days) {
        mDayPickerAdapter = new DayPickerAdapter(this, days);
        mDaysRecyclerView.setLayoutManager(llmDays);
        mDaysRecyclerView.setAdapter(mDayPickerAdapter);
    }

    @Override
    public void showGroups(ArrayList<Group> groups) {
        groupAutomationAdapter = new GroupAutomationAdapter(this, groups);
        mGroupsRecyclerView.setLayoutManager(llmGroups);
        mGroupsRecyclerView.setAdapter(groupAutomationAdapter);
    }

    @OnClick(R.id.selectEveryDay)
    public void selectEveryDay() {
        if (!daysSelected) {
            mDayPickerAdapter.selectAllDay();
            mSelectEveryDay.setText(R.string.deselect_days);
            daysSelected = true;
        } else {
            mDayPickerAdapter.deselectDays();
            mSelectEveryDay.setText(R.string.select_every_day);
            daysSelected = false;
        }
    }

    @OnClick(R.id.set_time_layout)
    public void showTimepicker() {
        mSunriseIcon.setVisibility(View.GONE);
        mSunsetIcon.setVisibility(View.GONE);
        mSetTimeIcon.setVisibility(View.VISIBLE);
        if (mTimepickerLayout.getVisibility() == View.VISIBLE) {
            mTimepickerLayout.setVisibility(View.GONE);
        } else {
            mTimepickerLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.sunset_layout)
    public void clickedOnSunset() {
        automation.setAutomation_time("sunset");
        mSunsetIcon.setVisibility(View.VISIBLE);
        mTimepickerLayout.setVisibility(View.GONE);
        mSetTimeIcon.setVisibility(View.GONE);
        mSunriseIcon.setVisibility(View.GONE);
    }

    @OnClick(R.id.sunrise_layout)
    public void clickedOnSunrise() {
        automation.setAutomation_time("sunset");
        mSunriseIcon.setVisibility(View.VISIBLE);
        mTimepickerLayout.setVisibility(View.GONE);
        mSetTimeIcon.setVisibility(View.GONE);
        mSunsetIcon.setVisibility(View.GONE);
    }

    @OnClick(R.id.save_automation)
    public void saveAutomation() {

        if (mAutomationName.getText().toString().isEmpty()) {
            mAutomationName.setError(getString(R.string.required_field), null);
            mAutomationName.requestFocus();
        } else if (mDayPickerAdapter.getSelectedItemsCount() == 0) {
            Toast.makeText(this, R.string.have_to_pick_one_day_at_least,
                    Toast.LENGTH_SHORT).show();
        } else if (groupAutomationAdapter.getSelectedItemsCount() == 0) {
            Toast.makeText(this, R.string.pick_one_room_at_least,
                    Toast.LENGTH_SHORT).show();
        } else {
            automation.setName(mAutomationName.getText().toString());
            automation.setPicked_days(mDayPickerAdapter.getSelectedDays());
            if (isUpperwardsMovement) {
                automation.setShutter_movement(Enums.ShutterMovement.UP);
            } else {
                automation.setShutter_movement(Enums.ShutterMovement.DOWN);
            }
            automation.setGroup(groupAutomationAdapter.getSelectedGroups());
            Log.i("Automation", "Clicked on save - Automation: " +
                    new Gson().toJson(automation));
            // TODO - API hívás
        }
    }

    @OnClick(R.id.cancel_automation)
    public void cancelAutomation() {
        finish();
    }

    @OnClick(R.id.select_room_layout)
    public void showGroupsLayout() {
        if (mGroupsLayout.getVisibility() == View.VISIBLE) {
            mGroupsLayout.setVisibility(View.GONE);
        } else {
            mGroupsLayout.setVisibility(View.VISIBLE);
        }
    }

    public void updateSelectedItemsCount() {
        int count = groupAutomationAdapter.getSelectedItemsCount();
        String countText = String.format(getResources().getString(R.string._0_room), count);
        mCountText.setText(countText);
    }

}
