package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.Automation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AutomationService {

    @GET("automation")
    Call<Automation[]> getAutomations();

    @GET("automation/{id}")
    Call<Automation> getAutomation(@Path("id") int automationId);

    @POST("automation")
    Call<Automation> createAutomation(@Body Automation automation);

    @PUT("automation/{id}")
    Call<Automation> modifyAutomation(@Path("id") int automationId, @Body Automation automation);

    @DELETE("automation/{id}")
    Call<Void> deleteAutomation(@Path("id") int automationId);

}
