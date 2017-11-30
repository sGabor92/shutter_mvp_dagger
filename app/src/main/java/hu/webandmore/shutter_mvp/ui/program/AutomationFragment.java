package hu.webandmore.shutter_mvp.ui.program;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.adapter.AutomationAdapter;
import hu.webandmore.shutter_mvp.adapter.GroupsAdapter;
import hu.webandmore.shutter_mvp.adapter.ShutterAdapter;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.ui.automation.CreateAutomationAcivity;

public class AutomationFragment extends Fragment implements AutomationScreen {

    private static String TAG = "AutomationFragment";

    @BindView(R.id.addNewAutomation)
    Button mAddNewAutomationBtn;

    @BindView(R.id.noAutomationLayout)
    LinearLayout mNoAutomationView;

    @BindView(R.id.automation_list)
    RecyclerView mAutomationsRecyclerView;

    ArrayList<Automation> automations;
    AutomationAdapter automationAdapter;
    private LinearLayoutManager llmAutomations;

    private AutomationPresenter automationPresenter;

    public AutomationFragment() {

        automations = new ArrayList<>();
        llmAutomations = new LinearLayoutManager(getContext());
        llmAutomations.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_automation, container, false);

        ButterKnife.bind(this, view);

        automationPresenter = new AutomationPresenter(getContext());
        automationPresenter.attachScreen(this);

        //fillTestAutomations();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "ONRESUME CALLING");
        automationPresenter.getAutomations();
    }

    @OnClick(R.id.addNewAutomation)
    public void addNewAutomation() {
        Log.i(TAG, "Clicked on add new automation!");
        Intent intent = new Intent(getContext(), CreateAutomationAcivity.class);
        startActivity(intent);
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
    public void showAutomations(ArrayList<Automation> automations) {
        if(automations.size() == 0) {
            mNoAutomationView.setVisibility(View.VISIBLE);
        } else {
            mNoAutomationView.setVisibility(View.GONE);
            automationAdapter = new AutomationAdapter(getContext(), automations);
            mAutomationsRecyclerView.setLayoutManager(llmAutomations);
            mAutomationsRecyclerView.setAdapter(automationAdapter);
            automationPresenter.initSwipe();
        }
    }

    @Override
    public RecyclerView getAutomationRecyclerView() {
        return mAutomationsRecyclerView;
    }

    @Override
    public void removeAutomation(int position) {
        automationAdapter.removeItem(position);
    }

    @Override
    public void restoreAutomation(int position) {
        Automation removedAutomation = automationAdapter.getItem(position);
        automationAdapter.removeItem(position);
        automationAdapter.restoreItem(position, removedAutomation);
    }

    @Override
    public int getSelectedAutomationId(int position) {
        return automationAdapter.getCurrentId(position);
    }
}
