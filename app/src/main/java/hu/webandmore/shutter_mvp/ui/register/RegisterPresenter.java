package hu.webandmore.shutter_mvp.ui.register;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import hu.webandmore.shutter_mvp.app.ShutterApplication;
import hu.webandmore.shutter_mvp.interactor.RegisterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.RegisterEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;
import hu.webandmore.shutter_mvp.utils.PrefUtils;

public class RegisterPresenter extends Presenter<RegisterScreen>{

    //private static String TAG = "RegisterPresenter";

    private Executor networkExecutor;
    private Context context;

    @Inject
    RegisterInteractor registerInteractor;

    public RegisterPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        ShutterApplication.injector.inject(this);
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
                screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if(event.getCode() == 200) {
                    screen.userRegistered();
                } else {
                    screen.showError(event.getErrorMessage());
                    screen.hideProgressBar();
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
