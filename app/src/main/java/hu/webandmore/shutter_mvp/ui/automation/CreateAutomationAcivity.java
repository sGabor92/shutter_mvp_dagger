package hu.webandmore.shutter_mvp.ui.automation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.DayPickerAdapter;
import hu.webandmore.shutter_mvp.adapter.GroupsAdapter;
import hu.webandmore.shutter_mvp.adapter.ShutterAdapter;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.ui.program.ShutterCenterPresenter;

public class CreateAutomationAcivity extends AppCompatActivity implements CreateAutomationScreen {

    @BindView(R.id.daysRecyclerView)
    RecyclerView mDaysRecyclerView;

    @BindView(R.id.selectEveryDay)
    Button mSelectEveryDay;

    ArrayList<PickedDay> mDays;
    DayPickerAdapter mDayPickerAdapter;
    private LinearLayoutManager llmDays;

    CreateAutomationPresenter createAutomationPresenter;

    private boolean daysSelected = false;

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

        createAutomationPresenter = new CreateAutomationPresenter(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        createAutomationPresenter.attachScreen(this);
        createAutomationPresenter.fillDays();
    }

    @Override
    protected void onStop() {
        super.onStop();
        createAutomationPresenter.detachScreen();
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

    @OnClick(R.id.selectEveryDay)
    public void selectEveryDay() {
        if(!daysSelected) {
            mDayPickerAdapter.selectAllDay();
            mSelectEveryDay.setText(R.string.deselect_days);
            daysSelected = true;
        } else {
            mDayPickerAdapter.deselectDays();
            mSelectEveryDay.setText(R.string.select_every_day);
            daysSelected = false;
        }
    }
}
