package hu.webandmore.shutter_mvp.ui.register;

interface RegisterScreen {

    String getEmail();
    String getPassword();
    String getPasswordAgain();
    void attemptRegister();
    void showError(String errorMsg);
    void userRegistered();
    void showProgressBar();
    void hideProgressBar();
}
