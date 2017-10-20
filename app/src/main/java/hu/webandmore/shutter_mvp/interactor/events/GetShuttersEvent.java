package hu.webandmore.shutter_mvp.interactor.events;

import java.util.ArrayList;
import java.util.Collections;

import hu.webandmore.shutter_mvp.api.model.Channel;

public class GetShuttersEvent {

    private int code;
    private ArrayList<Channel> shutters;
    private Throwable throwable;
    private String errorMessage;

    public GetShuttersEvent() {
    }

    public GetShuttersEvent(int code, ArrayList<Channel> shutters, Throwable t) {
        this.code = code;
        this.shutters = shutters;
        this.throwable = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Channel> getShutters() {
        return shutters;
    }

    public void setShutters(Channel[] channels) {
        Collections.addAll(this.shutters, channels);
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
