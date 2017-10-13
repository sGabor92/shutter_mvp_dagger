package hu.webandmore.shutter_mvp.app;

import android.app.Application;

import hu.webandmore.shutter_mvp.dagger.AppComponent;
import hu.webandmore.shutter_mvp.dagger.AppModule;
import hu.webandmore.shutter_mvp.dagger.DaggerAppComponent;

public class ShutterApplication extends Application{

    public static AppComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerAppComponent.builder().
                        appModule(
                                new AppModule(this)
                        ).build();
    }
}
