package hu.webandmore.shutter_mvp.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import hu.webandmore.shutter_mvp.interactor.LoginInteractor;
import hu.webandmore.shutter_mvp.interactor.RegisterInteractor;

@Module
public class InteractorModule {

    private Context context;

    InteractorModule(Context context){
        this.context = context;
    }

    @Provides
    public LoginInteractor provideLoginInteractor(){
        return new LoginInteractor(context);
    }

    @Provides
    public RegisterInteractor provideRegisterInteractor(){
        return new RegisterInteractor(context);
    }

}
