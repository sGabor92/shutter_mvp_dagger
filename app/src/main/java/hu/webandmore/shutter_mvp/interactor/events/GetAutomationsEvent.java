package hu.webandmore.shutter_mvp.interactor.events;

import java.util.ArrayList;
import java.util.Collections;

import hu.webandmore.shutter_mvp.api.model.Automation;

public class GetAutomationsEvent {

    private int code;
    private ArrayList<Automation> automations;
    private Throwable throwable;
    private String errorMessage;

    public GetAutomationsEvent() {
        this.automations = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Automation> getAutomations() {
        return automations;
    }

    public void setAutomations(Automation[] automations) {
        Collections.addAll(this.automations, automations);
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
