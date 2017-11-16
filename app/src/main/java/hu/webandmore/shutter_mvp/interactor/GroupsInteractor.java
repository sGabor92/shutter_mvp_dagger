package hu.webandmore.shutter_mvp.interactor;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.UnknownHostException;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.api.model.Channel;
import hu.webandmore.shutter_mvp.api.model.Group;
import hu.webandmore.shutter_mvp.api.services.ShutterGroupsService;
import hu.webandmore.shutter_mvp.interactor.events.AttachChannelToGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.CreateGroupEvent;
import hu.webandmore.shutter_mvp.interactor.events.CreateShutterEvent;
import hu.webandmore.shutter_mvp.interactor.events.DetachChannelToGroupEvent;
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

    public void createGroup(Group group) {
        Call<Group> call = shutterGroupsService.createGroup(group);

        final CreateGroupEvent createGroupEvent = new CreateGroupEvent();
        call.enqueue(new Callback<Group>() {

            @Override
            public void onResponse(Call<Group> call, Response<Group> result) {
                if (result.isSuccessful()) {
                    createGroupEvent.setCode(result.code());
                    createGroupEvent.setGroup(result.body());
                    EventBus.getDefault().post(createGroupEvent);
                } else {
                    createGroupEvent.setCode(result.code());
                    try {
                        createGroupEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(createGroupEvent);
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                createGroupEvent.setErrorMessage(errorMessage);
                createGroupEvent.setThrowable(t);
                EventBus.getDefault().post(createGroupEvent);
            }
        });
    }

    public void attachChannelToGroup(Group group, int channelId) {
        Call<Group> call = shutterGroupsService.attachChannelToGroup(group.getId(), channelId);

        final AttachChannelToGroupEvent attachChannelToGroupEvent = new AttachChannelToGroupEvent();
        call.enqueue(new Callback<Group>() {

            @Override
            public void onResponse(Call<Group> call, Response<Group> result) {
                if (result.isSuccessful()) {
                    attachChannelToGroupEvent.setCode(result.code());
                    EventBus.getDefault().post(attachChannelToGroupEvent);
                } else {
                    attachChannelToGroupEvent.setCode(result.code());
                    try {
                        attachChannelToGroupEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(attachChannelToGroupEvent);
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                attachChannelToGroupEvent.setErrorMessage(errorMessage);
                attachChannelToGroupEvent.setThrowable(t);
                EventBus.getDefault().post(attachChannelToGroupEvent);
            }
        });
    }

    public void detachChannelFromGroup(Group group, int channelId) {
        Call<Group> call = shutterGroupsService.removeChannelFromGroup(group.getId(), channelId);

        final DetachChannelToGroupEvent detachChannelToGroupEvent = new DetachChannelToGroupEvent();
        call.enqueue(new Callback<Group>() {

            @Override
            public void onResponse(Call<Group> call, Response<Group> result) {
                if (result.isSuccessful()) {
                    detachChannelToGroupEvent.setCode(result.code());
                    EventBus.getDefault().post(detachChannelToGroupEvent);
                } else {
                    detachChannelToGroupEvent.setCode(result.code());
                    try {
                        detachChannelToGroupEvent.setErrorMessage(result.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(detachChannelToGroupEvent);
                }
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {

                String errorMessage = context.getString(R.string.error_during_login);
                if (t instanceof UnknownHostException) {
                    errorMessage = context.getString(R.string.network_error);
                } else if (t instanceof IOException) {
                    errorMessage = context.getString(R.string.internal_server_error);
                }
                detachChannelToGroupEvent.setErrorMessage(errorMessage);
                detachChannelToGroupEvent.setThrowable(t);
                EventBus.getDefault().post(detachChannelToGroupEvent);
            }
        });
    }
}
