package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.Channel;

public class ModifyShutterEvent {

    private int code;
    private Channel modifiedShutter;
    private Throwable throwable;
    private String errorMessage;

    public ModifyShutterEvent() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Channel getShutter() {
        return modifiedShutter;
    }

    public void setShutter(Channel modifiedShutter) {
        this.modifiedShutter = modifiedShutter;
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
