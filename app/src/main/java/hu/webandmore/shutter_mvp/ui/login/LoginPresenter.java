package hu.webandmore.shutter_mvp.ui.login;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.interactor.LoginInteractor;
import hu.webandmore.shutter_mvp.interactor.events.LoginEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.PrefUtils;
import hu.webandmore.shutter_mvp.utils.TokenStorage;

public class LoginPresenter extends Presenter<LoginScreen> {

    private static String TAG = "LoginPresenter";

    private Executor networkExecutor;
    private LoginInteractor loginInteractor;
    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        loginInteractor = new LoginInteractor(context);
    }

    @Override
    public void attachScreen(LoginScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    public boolean hasLogin(LoginScreen screen){
        Log.i(TAG, "Calling hasLogin");
        String userName = PrefUtils.getFromPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_USERNAME_KEY,
                "");
        String password = PrefUtils.getFromPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_PASSWORD_KEY,
                "");
        if (!userName.equals("") && !password.equals("")) {
            screen.setEmail(userName);
            screen.setPassword(password);
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmailValid(String email){
        return email.contains("@");
    }

    public boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    public void loginUser(final String email, final String password){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loginInteractor.loginUser(email, password);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final LoginEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if(event.getCode() == 200) {
                    screen.userLoggedIn(event.getUser().getApi_token());
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

    public void loginFinished(LoginScreen screen, final String token, TokenStorage mToken) {
        PrefUtils.saveToPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_USERNAME_KEY,
                screen.getEmail());
        PrefUtils.saveToPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_PASSWORD_KEY,
                screen.getPassword());
        PrefUtils.saveToPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_TOKEN,
                token);

        mToken.setToken(context, token);
    }

}
