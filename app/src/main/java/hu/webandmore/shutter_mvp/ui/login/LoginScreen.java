package hu.webandmore.shutter_mvp.ui.login;

import hu.webandmore.shutter_mvp.api.model.User;

public interface LoginScreen {

    void loginUser(User user);
    void checkLogin();
    void switchToRegister();
    void showError(String errorMsg);

}
