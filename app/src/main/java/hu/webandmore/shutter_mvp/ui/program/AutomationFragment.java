package hu.webandmore.shutter_mvp.ui.program;

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

public class AutomationFragment extends Fragment {

    private static String TAG = "AutomationFragment";

    @BindView(R.id.addNewAutomation)
    Button addNewAutomationBtn;

    public AutomationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_automation, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @OnClick(R.id.addNewAutomation)
    public void addNewAutomation() {
        Log.i(TAG, "Clicked on add new automation!");
    }

}
