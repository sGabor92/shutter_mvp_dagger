package hu.webandmore.shutter_mvp.ui.automation;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class CreateAutomationPresenter extends Presenter<CreateAutomationScreen> {

    private Context context;

    CreateAutomationPresenter(Context context) {
        this.context = context;
    }

    public void fillDays() {
        ArrayList<PickedDay> days = new ArrayList<>();
        List<String> daysString = Arrays.asList(context.getResources().getStringArray(R.array.days_array));
        for(int i = 0; i < daysString.size(); i++) {
            PickedDay pickedDay = new PickedDay(i, daysString.get(i), false);
            days.add(pickedDay);
        }
        screen.showDays(days);
    }

}
