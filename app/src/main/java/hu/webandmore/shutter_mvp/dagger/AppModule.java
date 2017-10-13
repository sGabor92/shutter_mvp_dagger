package hu.webandmore.shutter_mvp.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.webandmore.shutter_mvp.ui.login.LoginPresenter;
import hu.webandmore.shutter_mvp.ui.register.RegisterPresenter;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(context);
    }

    @Provides
    @Singleton
    public RegisterPresenter provideRegisterPresenter(){
        return new RegisterPresenter(context);
    }

}
