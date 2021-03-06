package hu.webandmore.shutter_mvp.ui.groups;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;

interface EditGroupScreen {
    void showShutters(ArrayList<Channel> shutters);
    void showError(String errorMessage);
    void hideProgressBar();
    void showProgressBar();
    void savedSuccessful();
    void channelAttached();
    void channelDetached();
}
