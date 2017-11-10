package hu.webandmore.shutter_mvp.ui.program;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.api.model.Group;

public interface ShutterCenterScreen {

    void showError(String errorMessage);
    void showProgressBar();
    void hideProgressBar();
    void showShutters(ArrayList<Channel> shutters);
    void activateShutters();
    void showGroups(ArrayList<Group> groups);
}
