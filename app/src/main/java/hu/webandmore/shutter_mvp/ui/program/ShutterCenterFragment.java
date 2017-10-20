package hu.webandmore.shutter_mvp.ui.program;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.webandmore.shutter_mvp.R;

public class ShutterCenterFragment extends Fragment{

    public ShutterCenterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shutter_center, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
