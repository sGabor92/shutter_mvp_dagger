package hu.webandmore.shutter_mvp.ui.program;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;

public interface ShutterCenterScreen {

    void showError(String errorMessage);
    void getShutters();
    void showProgressBar();
    void hideProgressBar();
    void showShutters(ArrayList<Channel> shutters);

}
