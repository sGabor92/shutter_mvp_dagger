package hu.webandmore.shutter_mvp.ui.register;

import android.content.Context;

import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.PrefUtils;

class RegisterPresenter extends Presenter<RegisterScreen>{

    private static String TAG = "RegisterPresenter";

    private Context context;

    RegisterPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachScreen(RegisterScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    boolean isEmailValid(String email){
        return email.contains("@");
    }

    boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    boolean isPasswordEqual(String password, String passwordAgain) {
        return password.equals(passwordAgain);
    }

    void registerUser(String email, String password, String passwordAgain){

    }

    void registerFinished(RegisterScreen screen) {
        PrefUtils.saveToPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_USERNAME_KEY,
                screen.getEmail());
        PrefUtils.saveToPrefs(
                context.getApplicationContext(),
                PrefUtils.PREFS_LOGIN_PASSWORD_KEY,
                screen.getPassword());
    }

}
