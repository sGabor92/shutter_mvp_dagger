package hu.webandmore.shutter_mvp.ui.program;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.webandmore.shutter_mvp.R;

public class AutomationFragment extends Fragment {

    private static String TAG = "AutomationFragment";
    Button addNewAutomation;

    public AutomationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_automation, container, false);
        addNewAutomation = (Button) view.findViewById(R.id.addNewAutomation);
        addNewAutomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked on add new!");
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
