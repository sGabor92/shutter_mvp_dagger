package hu.webandmore.shutter_mvp.interactor.events;

import java.util.ArrayList;
import java.util.Collections;

import hu.webandmore.shutter_mvp.api.model.PickedDay;

public class GetAutomationDaysEvent {

    private int code;
    private ArrayList<PickedDay> automationDays;
    private Throwable throwable;
    private String errorMessage;

    public GetAutomationDaysEvent() {
        this.automationDays = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<PickedDay> getAutomationDays() {
        return automationDays;
    }

    public void setAutomations(PickedDay[] automations) {
        Collections.addAll(this.automationDays, automations);
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
