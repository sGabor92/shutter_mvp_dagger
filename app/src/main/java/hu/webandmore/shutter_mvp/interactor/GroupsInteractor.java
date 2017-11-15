package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.services.ShutterGroupsService;
import hu.webandmore.shutter_mvp.interactor.events.GetGroupsEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsInteractor {

    private ShutterGroupsService shutterGroupsService;
    private Context context;

    public GroupsInteractor(Context context) {
        this.context = context;
        this.shutterGroupsService = ServiceGenerator.createService(ServiceGenerator.ApiType.LOCAL,
                context, ShutterGroupsService.class);
    }

    public void getGroups() {
        Call<Group[]> call = shutterGroupsService.getGroups();

        final GetGroupsEvent getGroupsEvent = new GetGroupsEvent();

        call.enqueue(new Callback<Group[]>() {
            @Override
            public void onResponse(Call<Group[]> call, Response<Group[]> result) {
                if (result.isSuccessful()) {
                    getGroupsEvent.setGroups(result.body());
                    getGroupsEvent.setCode(result.code());
                    EventBus.getDefault().post(getGroupsEvent);
                } else {
                    getGroupsEvent.setCode(result.code());
                    try {
                        getGroupsEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(getGroupsEvent);
                }
            }

            @Override
            public void onFailure(Call<Group[]> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                getGroupsEvent.setErrorMessage(errorMessage);
                getGroupsEvent.setThrowable(t);
                EventBus.getDefault().post(getGroupsEvent);
            }
        });
    }
}
