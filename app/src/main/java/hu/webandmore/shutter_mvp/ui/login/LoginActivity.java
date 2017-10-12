package hu.webandmore.shutter_mvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hu.webandmore.shutter_mvp.MainActivity;
import hu.webandmore.shutter_mvp.R;
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
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
        mToken = new TokenStorage(this);

        checkLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.detachScreen();
    }

    @OnClick(R.id.login)
    public void login(View view) {
        attemptLogin();
    }

    @OnClick(R.id.signUp)
    public void signUp(View view) {
        Log.i(TAG, "Click on SignUp text!");
    }

    @OnEditorAction(R.id.password)
    public boolean onPasswordAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            attemptLogin();
            return true;
        }
        return false;
    }

    @Override
    public void checkLogin() {
        if (loginPresenter.hasLogin(this)) {
            attemptLogin();
        } else {
            Log.i(TAG, "No Login");
            //Utils.showProgress(this, false, mMainView, mProgressView);
        }
    }

    @Override
    public void setEmail(String email) {
        mEmailView.setText(email);
    }

    @Override
    public String getEmail() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        mPasswordView.setText(password);
    }

    @Override
    public void attemptLogin() {
        mPasswordView.setError(null);
        mEmailView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!loginPresenter.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.password_too_short), null);
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.invalid_email_address), null);
            focusView = mEmailView;
            cancel = true;
        } else if (!loginPresenter.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_email_address), null);
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            loginPresenter.loginUser(email, password);
        }
    }

    @Override
    public void switchToRegister() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userLoggedIn(String token) {
        loginPresenter.loginFinished(this, token, mToken);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
