package hu.webandmore.shutter_mvp.ui.register;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.interactor.RegisterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.RegisterEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.PrefUtils;

class RegisterPresenter extends Presenter<RegisterScreen>{

    private static String TAG = "RegisterPresenter";

    private Executor networkExecutor;
    private RegisterInteractor registerInteractor;
    private Context context;

    RegisterPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        registerInteractor = new RegisterInteractor(context);
    }

    @Override
    public void attachScreen(RegisterScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    boolean isEmailValid(String email){
        return email.contains("@");
    }

    boolean isPasswordValid(String password){
        return password.length() > 5;
    }

    boolean isPasswordEqual(String password, String passwordAgain) {
        return password.equals(passwordAgain);
    }

    void registerUser(final String email, final String password, final String passwordAgain){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                registerInteractor.registerUser(email, password, passwordAgain);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final RegisterEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if(event.getCode() == 200) {
                    screen.userRegistered();
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
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
