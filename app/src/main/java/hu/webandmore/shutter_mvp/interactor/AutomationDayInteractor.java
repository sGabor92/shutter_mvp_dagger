package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.PickedDay;
import hu.webandmore.shutter_mvp.api.services.AutomationDaysService;
import hu.webandmore.shutter_mvp.interactor.events.GetAutomationDaysEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutomationDayInteractor {

    private AutomationDaysService automationDaysService;
    private Context context;

    public AutomationDayInteractor(Context context) {
        this.context = context;
        this.automationDaysService = ServiceGenerator.createService(ServiceGenerator.ApiType.LOCAL,
                context, AutomationDaysService.class);
    }

    public void getAutomations() {
        Call<PickedDay[]> call = automationDaysService.getAutomationDays();

        final GetAutomationDaysEvent getAutomationDaysEvent = new GetAutomationDaysEvent();

        call.enqueue(new Callback<PickedDay[]>() {
            @Override
            public void onResponse(Call<PickedDay[]> call, Response<PickedDay[]> result) {
                if (result.isSuccessful()) {
                    getAutomationDaysEvent.setAutomations(result.body());
                    getAutomationDaysEvent.setCode(result.code());
                    EventBus.getDefault().post(getAutomationDaysEvent);
                } else {
                    getAutomationDaysEvent.setCode(result.code());
                    try {
                        getAutomationDaysEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(getAutomationDaysEvent);
                }
            }

            @Override
            public void onFailure(Call<PickedDay[]> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                getAutomationDaysEvent.setErrorMessage(errorMessage);
                getAutomationDaysEvent.setThrowable(t);
                EventBus.getDefault().post(getAutomationDaysEvent);
            }
        });
    }
}
