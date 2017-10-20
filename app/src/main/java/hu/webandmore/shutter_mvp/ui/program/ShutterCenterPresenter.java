package hu.webandmore.shutter_mvp.ui.program;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import hu.webandmore.shutter_mvp.interactor.ShutterInteractor;
import hu.webandmore.shutter_mvp.interactor.events.GetShuttersEvent;
import hu.webandmore.shutter_mvp.ui.Presenter;

public class ShutterCenterPresenter extends Presenter<ShutterCenterScreen>{

    private static String TAG = "ShutterCenterPresenter";

    private Executor networkExecutor;
    private Context context;

    private ShutterInteractor shutterInteractor;

    public ShutterCenterPresenter(Context context) {
        this.context = context;
        networkExecutor = Executors.newFixedThreadPool(1);
        shutterInteractor = new ShutterInteractor(context);
        System.out.println("ATTACHSCREEN CONST LEFUT");
    }

    @Override
    public void attachScreen(ShutterCenterScreen screen) {
        super.attachScreen(screen);
        System.out.println("ATTACHSCREEN LEFUT");
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }

    void getShutters() {
        System.out.println("ATTACHSCREEN GETSHUTTERS LEFUT");
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
                System.out.println("ERROR");
                screen.showError(event.getThrowable().getMessage());
                screen.hideProgressBar();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    System.out.println("200 SUCCESS");
                    screen.showShutters(event.getShutters());
                } else {
                    System.out.println("ERROR NO THROWABLE");
                    screen.showError(event.getErrorMessage());
                    screen.hideProgressBar();
                }
            }
        }
    }

}
