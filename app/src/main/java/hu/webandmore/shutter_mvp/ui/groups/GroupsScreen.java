package hu.webandmore.shutter_mvp.ui.groups;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;

interface GroupsScreen {
    void showProgressBar();
    void hideProgressBar();
    void showError(String errorMsg);
    void showShutters(ArrayList<Channel> shutters);
}
