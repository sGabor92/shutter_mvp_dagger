package hu.webandmore.shutter_mvp.ui.login;


import android.content.Context;

import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.User;
import hu.webandmore.shutter_mvp.api.services.AuthService;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class LoginPresenter extends Presenter<LoginScreen> {

    private AuthService loginService;
    private static User loggedInUser = null;

    public LoginPresenter(Context context) {
        loginService = ServiceGenerator.createService(ServiceGenerator.ApiType.GLOBAl,
                context, AuthService.class);
    }

    @Override
    public void attachScreen(LoginScreen screen) {

        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public boolean isEmailValid(String email){
        return email.contains("@");
    }

    public boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    public void loginFinished(final String token) {

    }
}
