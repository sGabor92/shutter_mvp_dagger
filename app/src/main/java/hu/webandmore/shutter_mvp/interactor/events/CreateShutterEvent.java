package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.Channel;

public class CreateShutterEvent {

    private int code;
    private Channel shutter;
    private Throwable throwable;
    private String errorMessage;

    public CreateShutterEvent() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Channel getShutter() {
        return shutter;
    }

    public void setShutter(Channel shutter) {
        this.shutter =  shutter;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
