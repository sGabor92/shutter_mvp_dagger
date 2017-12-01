package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.Automation;

public class GetAutomationEvent {

    private int code;
    private Automation automation;
    private Throwable throwable;
    private String errorMessage;

    public GetAutomationEvent() {
        automation = new Automation();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Automation getAutomation() {
        return automation;
    }

    public void setAutomation(Automation automation) {
        automation = automation;
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
