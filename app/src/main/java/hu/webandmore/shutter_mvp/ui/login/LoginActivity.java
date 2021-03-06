package hu.webandmore.shutter_mvp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hu.webandmore.shutter_mvp.MainActivity;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.app.ShutterApplication;
import hu.webandmore.shutter_mvp.ui.nsd.SearchingDeviceActivity;
import hu.webandmore.shutter_mvp.ui.program.ProgramShutterActivity;
import hu.webandmore.shutter_mvp.ui.register.RegisterActivity;
import hu.webandmore.shutter_mvp.utils.TokenStorage;

public class LoginActivity extends AppCompatActivity implements LoginScreen {

    //private static String TAG = "LoginActivity";

    @BindView(R.id.email)
    EditText mEmailView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;

    @BindView(R.id.loginLayout)
    LinearLayout mMainView;

    private TokenStorage mToken = null;

    @Inject
    LoginPresenter loginPresenter;

    //private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ShutterApplication.injector.inject(this);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
        mToken = new TokenStorage(this);

        showProgressBar();
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
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
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
            hideProgressBar();
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
        showProgressBar();

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
            hideProgressBar();
            focusView.requestFocus();
        } else {
            loginPresenter.loginUser(email, password);
        }
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userLoggedIn(String token, boolean userHasDevice) {
        loginPresenter.loginFinished(this, token, mToken);
        Intent intent;
        if (userHasDevice) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, SearchingDeviceActivity.class);
            //intent = new Intent(this, ProgramShutterActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        mProgressView.setVisibility(View.VISIBLE);
        mMainView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        mProgressView.setVisibility(View.GONE);
        mMainView.setVisibility(View.VISIBLE);
    }

}
