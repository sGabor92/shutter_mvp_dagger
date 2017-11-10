package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.Group;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShutterGroupsService {

    @GET("groups")
    Call<Group[]> getGroups();

    @GET("groups/{id}")
    Call<Group> getGroup(@Path("id") int groupId);

    @POST("groups")
    Call<Group> createGroup();

    @PUT("groups/{id}")
    Call<Group> modifyGroup(@Path("id") int groupId, @Body Group group);

    @DELETE("groups/{id}")
    Call<Void> deleteGroup(@Path("id") int groupId);

    @PUT("groups/{groupId}/{channelId}")
    Call<Group> attachChannelToGroup(@Path("groupId") int groupId, @Path("channelId") int channelId,
                                     @Body Group group);

}
