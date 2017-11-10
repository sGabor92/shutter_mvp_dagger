package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.app.Enums;

public class CopySignalEvent {

    private int code;
    private Throwable throwable;
    private String errorMessage;
    private Enums.ShutterMovement movement;

    public CopySignalEvent() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public Enums.ShutterMovement getMovement() {
        return movement;
    }

    public void setMovement(Enums.ShutterMovement movement) {
        this.movement = movement;
    }
}
