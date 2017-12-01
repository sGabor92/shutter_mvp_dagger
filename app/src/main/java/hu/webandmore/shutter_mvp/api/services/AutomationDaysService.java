package hu.webandmore.shutter_mvp.api.services;

import hu.webandmore.shutter_mvp.api.model.PickedDay;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AutomationDaysService {

    @GET("automationdays")
    Call<PickedDay[]> getAutomationDays();

    @GET("automationdays/{id}")
    Call<PickedDay> getAutomationDay(@Path("id") int automationDayId);

}
