package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.Automation;
import hu.webandmore.shutter_mvp.api.services.AutomationService;
import hu.webandmore.shutter_mvp.interactor.events.CreateAutomationEvent;
import hu.webandmore.shutter_mvp.interactor.events.DeleteAutomationEvent;
import hu.webandmore.shutter_mvp.interactor.events.GetAutomationsEvent;
import hu.webandmore.shutter_mvp.interactor.events.ModifyAutomationEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutomationInteractor {

    private AutomationService automationService;
    private Context context;

    public AutomationInteractor(Context context) {
        this.context = context;
        this.automationService = ServiceGenerator.createService(ServiceGenerator.ApiType.LOCAL,
                context, AutomationService.class);
    }

    public void getAutomations() {
        Call<Automation[]> call = automationService.getAutomation();

        final GetAutomationsEvent getAutomationsEvent = new GetAutomationsEvent();

        call.enqueue(new Callback<Automation[]>() {
            @Override
            public void onResponse(Call<Automation[]> call, Response<Automation[]> result) {
                if (result.isSuccessful()) {
                    getAutomationsEvent.setAutomations(result.body());
                    getAutomationsEvent.setCode(result.code());
                    EventBus.getDefault().post(getAutomationsEvent);
                } else {
                    getAutomationsEvent.setCode(result.code());
                    try {
                        getAutomationsEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(getAutomationsEvent);
                }
            }

            @Override
            public void onFailure(Call<Automation[]> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                getAutomationsEvent.setErrorMessage(errorMessage);
                getAutomationsEvent.setThrowable(t);
                EventBus.getDefault().post(getAutomationsEvent);
            }
        });
    }

    public void deleteAutomation(int automationId, final int itemPosition) {
        Call<Void> call = automationService.deleteAutomation(automationId);

        final DeleteAutomationEvent deleteAutomationEvent = new DeleteAutomationEvent();
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> result) {
                if (result.isSuccessful()) {
                    deleteAutomationEvent.setCode(result.code());
                    deleteAutomationEvent.setItemPosition(itemPosition);
                    EventBus.getDefault().post(deleteAutomationEvent);
                } else {
                    deleteAutomationEvent.setCode(result.code());
                    try {
                        deleteAutomationEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(deleteAutomationEvent);
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
                deleteAutomationEvent.setErrorMessage(errorMessage);
                deleteAutomationEvent.setThrowable(t);
                EventBus.getDefault().post(deleteAutomationEvent);
            }
        });
    }

    public void createAutomation(Automation automation) {
        Call<Automation> call = automationService.createAutomation(automation);

        final CreateAutomationEvent createAutomationEvent = new CreateAutomationEvent();
        call.enqueue(new Callback<Automation>() {

            @Override
            public void onResponse(Call<Automation> call, Response<Automation> result) {
                if (result.isSuccessful()) {
                    createAutomationEvent.setCode(result.code());
                    createAutomationEvent.setAutomation(result.body());
                    EventBus.getDefault().post(createAutomationEvent);
                } else {
                    createAutomationEvent.setCode(result.code());
                    try {
                        createAutomationEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(createAutomationEvent);
                }
            }

            @Override
            public void onFailure(Call<Automation> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                createAutomationEvent.setErrorMessage(errorMessage);
                createAutomationEvent.setThrowable(t);
                EventBus.getDefault().post(createAutomationEvent);
            }
        });
    }

    public void modifyAutomation(Automation automation) {
        Call<Automation> call = automationService.modifyAutomation(automation.getId(), automation);

        final ModifyAutomationEvent modifyAutomationEvent = new ModifyAutomationEvent();
        call.enqueue(new Callback<Automation>() {

            @Override
            public void onResponse(Call<Automation> call, Response<Automation> result) {
                if (result.isSuccessful()) {
                    modifyAutomationEvent.setCode(result.code());
                    modifyAutomationEvent.setAutomation(result.body());
                    EventBus.getDefault().post(modifyAutomationEvent);
                } else {
                    modifyAutomationEvent.setCode(result.code());
                    try {
                        modifyAutomationEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(modifyAutomationEvent);
                }
            }

            @Override
            public void onFailure(Call<Automation> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                modifyAutomationEvent.setErrorMessage(errorMessage);
                modifyAutomationEvent.setThrowable(t);
                EventBus.getDefault().post(modifyAutomationEvent);
            }
        });
    }
}
