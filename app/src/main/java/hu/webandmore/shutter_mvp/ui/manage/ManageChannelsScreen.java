package hu.webandmore.shutter_mvp.ui.manage;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Channel;

interface ManageChannelsScreen {
    void showProgressBar();
    void hideProgressBar();
    void showError(String errorMsg);
    void showShutters(ArrayList<Channel> shutters);
    void activateShutters();
    void removeShutter(int position);
    void restoreShutter(int position);
    RecyclerView getSavedShutters();
    int getSelectedShutterId(int position);
    void switchToCreatedChannel(int id);
}
