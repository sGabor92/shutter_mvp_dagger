package hu.webandmore.shutter_mvp.ui.login;

public interface LoginScreen {

    void checkLogin();
    void setEmail(String email);
    String getEmail();
    String getPassword();
    void setPassword(String password);
    void attemptLogin();
    void switchToRegister();
    void showError(String errorMsg);
    void userLoggedIn(String token);

}
