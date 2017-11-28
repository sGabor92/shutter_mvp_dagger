package hu.webandmore.shutter_mvp.ui.automation;


import java.util.ArrayList;

import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.model.PickedDay;

interface CreateAutomationScreen {
    void showError(String errorMessage);
    void showProgressBar();
    void hideProgressBar();
    void showDays(ArrayList<PickedDay> days);
    void showGroups(ArrayList<Group> groups);
}
