package hu.webandmore.shutter_mvp.ui.automation;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.interactor.AutomationDayInteractor;
import hu.webandmore.shutter_mvp.interactor.AutomationInteractor;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.interactor.events.CreateAutomationEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetAutomationDaysEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetAutomationEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetGroupsEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetShuttersEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class CreateAutomationPresenter extends Presenter<CreateAutomationScreen> {

    private Context context;
    private Executor networkExecutor;
    private GroupsInteractor groupsInteractor;
    private AutomationInteractor automationInteractor;
    private AutomationDayInteractor automationDayInteractor;

    CreateAutomationPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        groupsInteractor = new GroupsInteractor(context);
        automationInteractor = new AutomationInteractor(context);
        automationDayInteractor = new AutomationDayInteractor(context);
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

    @Override
    public void attachScreen(CreateAutomationScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getGroups() {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupsInteractor.getGroups();
            }
        });
    }

    void getAutomationDays() {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                automationDayInteractor.getAutomations();
            }
        });
    }

    void createAutomation(final Automation automation) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                automationInteractor.createAutomation(automation);
            }
        });
    }

    void getAutomation(final int automationId) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                automationInteractor.getAutomation(automationId);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetGroupsEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showGroups(event.getGroups());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final CreateAutomationEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.backToList();
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetAutomationEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.fillAutomationData(event.getAutomation());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetAutomationDaysEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    Log.i("DAYS", new Gson().toJson(event.getAutomationDays()));
                    screen.showDays(event.getAutomationDays());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

}
