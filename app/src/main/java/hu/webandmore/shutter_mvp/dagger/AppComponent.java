package hu.webandmore.shutter_mvp.dagger;


import javax.inject.Singleton;

import dagger.Component;
import hu.webandmore.shutter_mvp.MainActivity;
import hu.webandmore.shutter_mvp.interactor.LoginInteractor;
import hu.webandmore.shutter_mvp.interactor.RegisterInteractor;
import hu.webandmore.shutter_mvp.ui.login.LoginActivity;
import hu.webandmore.shutter_mvp.ui.register.RegisterActivity;

@Singleton
@Component(modules = {AppModule.class, InteractorModule.class})
public interface AppComponent {

    void inject(MainActivity target);
    void inject(LoginActivity target);
    void inject(RegisterActivity target);
    void inject(LoginInteractor loginInteractor);
    void inject(RegisterInteractor registerInteractor);

}
