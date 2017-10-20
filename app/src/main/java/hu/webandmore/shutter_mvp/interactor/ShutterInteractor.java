package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.api.services.ShutterService;
import hu.webandmore.shutter_mvp.interactor.events.GetShuttersEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShutterInteractor {

    private ShutterService shutterService;
    private Context context;

    public ShutterInteractor(Context context) {
        this.context = context;
        this.shutterService = ServiceGenerator.createService(ServiceGenerator.ApiType.LOCAL,
                context, ShutterService.class);
    }

    public void getShutters() {
        Call<Channel[]> call = shutterService.getChannels();

        final GetShuttersEvent getShuttersEvent = new GetShuttersEvent();

        call.enqueue(new Callback<Channel[]>() {
            @Override
            public void onResponse(Call<Channel[]> call, Response<Channel[]> result) {
                if (result.isSuccessful()) {
                    getShuttersEvent.setShutters(result.body());
                    getShuttersEvent.setCode(result.code());
                    EventBus.getDefault().post(getShuttersEvent);
                } else {
                    getShuttersEvent.setCode(result.code());
                    try {
                        getShuttersEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(getShuttersEvent);
                }
            }

            @Override
            public void onFailure(Call<Channel[]> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                getShuttersEvent.setErrorMessage(errorMessage);
                getShuttersEvent.setThrowable(t);
                EventBus.getDefault().post(getShuttersEvent);
            }
        });
    }
}
