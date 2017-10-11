package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("auth/register")
    Call<Void> register(@Body User user);

}
