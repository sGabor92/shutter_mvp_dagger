package hu.webandmore.shutter_mvp.ui.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.User;
import hu.webandmore.shutter_mvp.utils.TokenStorage;

public class LoginActivity extends AppCompatActivity implements LoginScreen {

    private static String TAG = "LoginActivity";

    @BindView(R.id.email)
    EditText mEmailView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;

    @BindView(R.id.loginLayout)
    LinearLayout mMainView;

    private TokenStorage mToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @OnClick(R.id.login)
    public void login(View view) {
        Log.i(TAG, "Click on login btn!");
    }

    @OnClick(R.id.signUp)
    public void signUp(View view) {
        Log.i(TAG, "Click on SignUp text!");
    }

    @OnEditorAction(R.id.password)
    public boolean onPasswordAction(int actionId) {
        Log.i(TAG, "After password!");
        if (actionId == EditorInfo.IME_ACTION_GO) {
            //attemptLogin();
            Log.i(TAG, "After password!");
            return true;
        }
        return false;
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
