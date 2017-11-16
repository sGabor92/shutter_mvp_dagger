package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.Group;

public class ModifyGroupEvent {

    private int code;
    private Group modifiedGroup;
    private Throwable throwable;
    private String errorMessage;

    public ModifyGroupEvent() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Group getGroup() {
        return modifiedGroup;
    }

    public void setGroup(Group modifiedGroup) {
        this.modifiedGroup = modifiedGroup;
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
