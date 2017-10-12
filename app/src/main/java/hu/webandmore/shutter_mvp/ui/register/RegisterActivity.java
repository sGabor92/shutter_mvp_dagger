package hu.webandmore.shutter_mvp.ui.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterScreen {

    private static String TAG = "RegisterActivity";

    @BindView(R.id.email)
    EditText mEmailView;

    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.password_again)
    EditText mPasswordAgainView;

    @BindView(R.id.register_progress)
    ProgressBar mProgressView;

    @BindView(R.id.registerLayout)
    LinearLayout mMainView;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenter();

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
        Log.i(TAG, "Click on register button");
    }

    @OnClick(R.id.signIn)
    public void signIn() {
        Log.i(TAG, "Click on SignUp text!");
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @OnEditorAction(R.id.password_again)
    public boolean onPasswordAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            Log.i(TAG, "After password again");
            return true;
        }
        return false;
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