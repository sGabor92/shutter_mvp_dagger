package hu.webandmore.shutter_mvp.ui.groups;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Group;

interface GroupsScreen {
    void showProgressBar();
    void hideProgressBar();
    void showError(String errorMsg);
    void showGroups(ArrayList<Group> groups);
    void savedSuccessful();
}
