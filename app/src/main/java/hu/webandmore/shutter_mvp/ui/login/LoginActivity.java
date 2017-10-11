package hu.webandmore.shutter_mvp.ui.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.User;

public class LoginActivity extends AppCompatActivity implements LoginScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void loginUser(User user) {

    }

    @Override
    public void checkLogin() {

    }

    @Override
    public void switchToRegister() {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
