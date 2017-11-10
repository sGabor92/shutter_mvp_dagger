package hu.webandmore.shutter_mvp.ui.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Channel;

public class GroupsActivity extends AppCompatActivity implements GroupsScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
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
    public void showShutters(ArrayList<Channel> shutters) {

    }
}
