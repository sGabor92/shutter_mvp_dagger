package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth/login")
    Call<User> login(@Body User user);

}
