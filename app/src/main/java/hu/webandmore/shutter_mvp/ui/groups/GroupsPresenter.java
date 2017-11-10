package hu.webandmore.shutter_mvp.ui.groups;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.interactor.events.GetGroupsEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class GroupsPresenter extends Presenter<GroupsScreen> {

    private static String TAG = "GroupsPresenter";

    private Executor networkExecutor;
    private Context context;

    private GroupsInteractor groupsInteractor;

    GroupsPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        groupsInteractor = new GroupsInteractor(context);
    }

    @Override
    public void attachScreen(GroupsScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getGroups() {
        System.out.println("In getGroups");
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupsInteractor.getGroups();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetGroupsEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    System.out.println("SHOW GROUPS: " + event.getGroups().size());
                    screen.showGroups(event.getGroups());
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

}
