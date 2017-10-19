package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.User;
import hu.webandmore.shutter_mvp.api.services.RegisterService;
import hu.webandmore.shutter_mvp.app.ShutterApplication;
import hu.webandmore.shutter_mvp.interactor.events.RegisterEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterInteractor {

    private RegisterService registerService;
    private Context context;

    public RegisterInteractor(Context context) {
        ShutterApplication.injector.inject(this);
        this.context = context;
        this.registerService = ServiceGenerator.createService(ServiceGenerator.ApiType.GLOBAl,
                context, RegisterService.class);
    }

    public void registerUser(String mEmail, String mPassword, String mPasswordAgain) {

        User user = new User();
        user.setName("Placeholder Name");
        user.setEmail(mEmail);
        user.setPassword(mPassword);
        user.setPassword_confirmation(mPasswordAgain);

        Call<Void> call = registerService.register(user);

        final RegisterEvent registerEvent = new RegisterEvent();

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> result) {
                if (result.isSuccessful()) {
                    registerEvent.setCode(result.code());
                    EventBus.getDefault().post(registerEvent);
                } else {
                    registerEvent.setCode(result.code());
                    try {
                        registerEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(registerEvent);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                registerEvent.setErrorMessage(errorMessage);
                registerEvent.setThrowable(t);
                EventBus.getDefault().post(registerEvent);
            }
        });

    }
}
