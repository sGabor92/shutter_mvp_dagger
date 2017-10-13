package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.User;
import hu.webandmore.shutter_mvp.api.services.AuthService;
import hu.webandmore.shutter_mvp.interactor.events.LoginEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractor {

    private AuthService loginService;
    private Context context;

    public LoginInteractor(Context context) {
        this.context = context;
        this.loginService = ServiceGenerator.createService(ServiceGenerator.ApiType.GLOBAl,
                context, AuthService.class);
    }

    public void loginUser(String mEmail, String mPassword) {

        User user = new User();
        user.setEmail(mEmail);
        user.setPassword(mPassword);
        Call<User> call = loginService.login(user);

        final LoginEvent loginEvent = new LoginEvent();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> result) {
                if (result.isSuccessful()) {
                    loginEvent.setUser(result.body());
                    loginEvent.setCode(result.code());
                    ServiceGenerator.setApiKey(context, loginEvent.getUser().getApi_token());
                    EventBus.getDefault().post(loginEvent);
                } else {
                    loginEvent.setCode(result.code());
                    try {
                        loginEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(loginEvent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                loginEvent.setErrorMessage(errorMessage);
                loginEvent.setThrowable(t);
                EventBus.getDefault().post(loginEvent);
            }
        });
    }

}
