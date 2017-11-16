package hu.webandmore.shutter_mvp.ui.groups;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;

interface ManageGroupScreen {

    void showShutters(ArrayList<Channel> shutters);
    void showError(String errorMessage);
    void hideProgressBar();
    void showProgressBar();
    void createGroup();
    void savedSuccessful();
}
