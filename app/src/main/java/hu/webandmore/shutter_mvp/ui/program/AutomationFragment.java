package hu.webandmore.shutter_mvp.ui.program;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.ui.automation.CreateAutomationAcivity;

public class AutomationFragment extends Fragment implements AutomationScreen {

    private static String TAG = "AutomationFragment";

    @BindView(R.id.addNewAutomation)
    Button mAddNewAutomationBtn;

    @BindView(R.id.noAutomationLayout)
    LinearLayout mNoAutomationView;

    ArrayList<Automation> automations;

    public AutomationFragment() {

        automations = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_automation, container, false);

        ButterKnife.bind(this, view);

        if(automations.size() == 0) {
            mNoAutomationView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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

    }
}
