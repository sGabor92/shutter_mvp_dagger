package hu.webandmore.shutter_mvp.ui.manage;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.ModifyShutterEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

class NewShutterPresenter extends Presenter<NewShutterScreen> {

    //private static String TAG = "NewShutterPresenter";

    private Executor networkExecutor;
    private Context context;

    private ShutterInteractor shutterInteractor;

    NewShutterPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        shutterInteractor = new ShutterInteractor(context);
    }

    @Override
    public void attachScreen(NewShutterScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    Channel createChannel(int channelId, String channelName) {
        Channel channel = new Channel();
        channel.setId(channelId);
        channel.setName(channelName);
        return channel;
    }

    void modifyChannel(final Channel channel) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                shutterInteractor.modifyShutter(channel);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final ModifyShutterEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showError(event.getThrowable().getMessage());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.startCopyingChannel();
                } else {
                    screen.showError(event.getErrorMessage());
                }
            }
        }
    }

}
