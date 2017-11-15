package hu.webandmore.shutter_mvp.ui.groups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hu.webandmore.shutter_mvp.R;

public class ManageGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
