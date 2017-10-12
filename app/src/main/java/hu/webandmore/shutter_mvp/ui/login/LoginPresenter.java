package hu.webandmore.shutter_mvp.ui.login;

import android.content.Context;
import android.util.Log;

import hu.webandmore.shutter_mvp.api.model.User;
import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.PrefUtils;
import hu.webandmore.shutter_mvp.utils.TokenStorage;

public class LoginPresenter extends Presenter<LoginScreen> {

    private static String TAG = "LoginPresenter";


    private static User loggedInUser = null;
    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachScreen(LoginScreen screen) {

        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
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
