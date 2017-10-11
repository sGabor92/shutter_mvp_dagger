package hu.webandmore.shutter_mvp.ui.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.User;

public class LoginActivity extends AppCompatActivity implements LoginScreen {

    @BindView(R.id.email)
    private EditText mEmailView;

    @BindView(R.id.password)
    private EditText mPasswordView;

    @BindView(R.id.login_progress)
    private ProgressBar mProgressView;

    @BindView(R.id.loginLayout)
    private LinearLayout mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
