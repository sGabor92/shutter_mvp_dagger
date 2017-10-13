package hu.webandmore.shutter_mvp.ui.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import hu.webandmore.shutter_mvp.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterScreen {

    //private static String TAG = "RegisterActivity";

    @BindView(R.id.email)
    EditText mEmailView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.password_again)
    EditText mPasswordAgainView;

    @BindView(R.id.register_progress)
    ProgressBar mProgressView;

    @BindView(R.id.registerLayout)
    LinearLayout mRegisterView;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerPresenter.detachScreen();
    }

    @OnClick(R.id.register)
    public void register() {
        attemptRegister();
    }

    @OnClick(R.id.signIn)
    public void signIn() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnEditorAction(R.id.password_again)
    public boolean onPasswordAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            attemptRegister();
            return true;
        }
        return false;
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
    public String getPasswordAgain() {
        return mPasswordAgainView.getText().toString();
    }

    @Override
    public void attemptRegister() {
        showProgressBar();

        mPasswordView.setError(null);
        mEmailView.setError(null);
        mPasswordAgainView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordAgain = mPasswordAgainView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.invalid_email_address), null);
            focusView = mEmailView;
            cancel = true;
        } else if (!registerPresenter.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_email_address), null);
            focusView = mEmailView;
            cancel = true;
        }
        if (!registerPresenter.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.password_too_short), null);
            focusView = mPasswordView;
            cancel = true;
        }
        if (!registerPresenter.isPasswordEqual(password, passwordAgain)) {
            mPasswordAgainView.setError(getString(R.string.different_passwords), null);
            focusView = mPasswordAgainView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            registerPresenter.registerUser(email, password, passwordAgain);
        }

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userRegistered() {
        registerPresenter.registerFinished(this);
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        mProgressView.setVisibility(View.VISIBLE);
        mRegisterView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        mProgressView.setVisibility(View.GONE);
        mRegisterView.setVisibility(View.VISIBLE);
    }
}
