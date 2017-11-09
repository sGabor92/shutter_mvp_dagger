package hu.webandmore.shutter_mvp.ui.manage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hu.webandmore.shutter_mvp.R;

public class NewShutterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shutter);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
