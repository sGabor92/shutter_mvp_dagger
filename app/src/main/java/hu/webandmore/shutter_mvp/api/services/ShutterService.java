package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.Channel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ShutterService {

    @GET("channel")
    Call<Channel[]> getChannels();

    @PUT("command/up")
    Call<Void> shutterUp(@Query("channel") int channel);

    @PUT("command/stop")
    Call<Void> shutterStop(@Query("channel") int channel);

    @PUT("command/down")
    Call<Void> shutterDown(@Query("channel") int channel);

    @POST("channel")
    Call<Channel> createChannel();

    @PUT
    Call<Channel> modifyChannel(@Url String url, @Body Channel channel);

    @POST("copy/up")
    Call<Void> copyUp(@Query("channel") int channel);

    @POST("copy/stop")
    Call<Void> copyStop(@Query("channel") int channel);

    @POST("copy/down")
    Call<Void> copyDown(@Query("channel") int channel);

    @DELETE("channel/{id}")
    Call<ResponseBody> deleteChannel(@Path("id") int bookId);

}
