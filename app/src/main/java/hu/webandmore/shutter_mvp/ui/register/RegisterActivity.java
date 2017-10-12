package hu.webandmore.shutter_mvp.ui.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hu.webandmore.shutter_mvp.R;

public class RegisterActivity extends AppCompatActivity implements RegisterScreen{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getPasswordAgain() {
        return null;
    }
}
