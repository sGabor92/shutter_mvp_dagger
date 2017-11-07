package hu.webandmore.shutter_mvp.ui.manage;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.GetShuttersEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

class ManageChannelsPresenter extends Presenter<ManageChannelsScreen> {

    private static String TAG = "ManageChannelsPresenter";

    private Executor networkExecutor;
    private Context context;

    private ShutterInteractor shutterInteractor;

    public ManageChannelsPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        shutterInteractor = new ShutterInteractor(context);
    }

    @Override
    public void attachScreen(ManageChannelsScreen screen) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final GetShuttersEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
                screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showShutters(event.getShutters());
                } else {
                    screen.showError(event.getErrorMessage());
                }
                screen.hideProgressBar();
            }
        }
    }

}
