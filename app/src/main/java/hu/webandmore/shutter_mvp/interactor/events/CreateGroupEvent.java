package hu.webandmore.shutter_mvp.interactor.events;

import hu.webandmore.shutter_mvp.api.model.Group;

public class CreateGroupEvent {

    private int code;
    private Group group;
    private Throwable throwable;
    private String errorMessage;

    public CreateGroupEvent() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group =  group;
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
