package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.User;

public class LoginEvent {

    private int code;
    private User user;
    private Throwable throwable;
    private String errorMessage;

    public LoginEvent() {
    }

    public LoginEvent(int code, User user, Throwable throwable) {
        this.code = code;
        this.user = user;
        this.throwable = throwable;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
