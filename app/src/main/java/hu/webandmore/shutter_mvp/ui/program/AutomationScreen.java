package hu.webandmore.shutter_mvp.ui.program;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Automation;

public interface AutomationScreen {
    void showError(String errorMessage);
    void showProgressBar();
    void hideProgressBar();
    void showAutomations(ArrayList<Automation> automations);
    RecyclerView getAutomationRecyclerView();
    void removeAutomation(int position);
    void restoreAutomation(int position);
    int getSelectedAutomationId(int position);
}
