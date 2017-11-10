package hu.webandmore.shutter_mvp.interactor.events;

import java.util.ArrayList;
import java.util.Collections;

import hu.webandmore.shutter_mvp.api.model.Group;

public class GetGroupsEvent {

    private int code;
    private ArrayList<Group> groups;
    private Throwable throwable;
    private String errorMessage;

    public GetGroupsEvent() {
        this.groups = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        Collections.addAll(this.groups, groups);
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
