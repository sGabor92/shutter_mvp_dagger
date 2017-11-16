package hu.webandmore.shutter_mvp.ui.groups;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.interactor.GroupsInteractor;
import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.AttachChannelToGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.CreateGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.DetachChannelToGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetShuttersEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class ManageGroupPresenter extends Presenter<ManageGroupScreen> {
    private static String TAG = "ManageGroupPresenter";

    private Executor networkExecutor;
    private Context context;

    private ShutterInteractor shutterInteractor;
    private GroupsInteractor groupsInteractor;

    ManageGroupPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        shutterInteractor = new ShutterInteractor(context);
        groupsInteractor = new GroupsInteractor(context);
    }

    @Override
    public void attachScreen(ManageGroupScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getShutters() {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                shutterInteractor.getShutters();
            }
        });
    }

    void createGroup(String groupName) {
        final Group group = new Group();
        group.setName(groupName);
        group.setColor("0x000000");
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupsInteractor.createGroup(group);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetShuttersEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showShutters(event.getShutters());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final CreateGroupEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.savedSuccessful();
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final AttachChannelToGroupEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.channelAttached();
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DetachChannelToGroupEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                //screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.channelDetached();
                } else {
                    screen.showError(event.getErrorMessage());
                }
                //screen.hideProgressBar();
            }
        }
    }

    // TODO - a gropu createhoz popup-ot visszarakni, és adapterben ha már a grouphoz tartozik akkor becheckeltetni az adott channelt

}
