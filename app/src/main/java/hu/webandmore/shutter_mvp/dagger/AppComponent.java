package hu.webandmore.shutter_mvp.dagger;


import javax.inject.Singleton;

import dagger.Component;
import hu.webandmore.shutter_mvp.MainActivity;
import hu.webandmore.shutter_mvp.interactor.LoginInteractor;
import hu.webandmore.shutter_mvp.interactor.RegisterInteractor;
import hu.webandmore.shutter_mvp.ui.login.LoginActivity;
import hu.webandmore.shutter_mvp.ui.login.LoginPresenter;
import hu.webandmore.shutter_mvp.ui.register.RegisterActivity;
import hu.webandmore.shutter_mvp.ui.register.RegisterPresenter;

@Singleton
@Component(modules = {AppModule.class, InteractorModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(LoginPresenter loginPresenter);
    void inject(RegisterActivity registerActivity);
    void inject(RegisterPresenter registerPresenter);
    void inject(LoginInteractor loginInteractor);
    void inject(RegisterInteractor registerInteractor);

}
