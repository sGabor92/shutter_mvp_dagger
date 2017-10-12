package hu.webandmore.shutter_mvp.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    
    public static String ADDRESS = "redonyinnyo-api.test.webandmore.hu";
    public static String DEV_ADDRESS = "192.168.1.9:8000";

    private static final String API_KEY = "api-key-storage";

    public enum ApiType {
        LOCAL, GLOBAl
    }

    public static <S> S createService(ApiType type, Context ctx, Class<S> serviceClass){
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpBuilder.addInterceptor(loggingInterceptor);

        final String apiKey = getApiKey(ctx);
        final Boolean hasApiKey = checkApiKey(ctx);
        httpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("User-Agent", "zyntern-android");
                if (hasApiKey) {
                    Log.i("APIKEY", apiKey);
                    requestBuilder.header("Authorization", "Bearer " + apiKey);
                }

                return chain.proceed(requestBuilder.build());
            }
        });

        String url = "http://" + ADDRESS + "/api/v1/";
        if (type.equals(ApiType.LOCAL))
            url = "http://" + DEV_ADDRESS + "/api/v1/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(httpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(serviceClass);
    }

    private static boolean checkApiKey(Context ctx)
    {
        return !getApiKey(ctx).equals("");
    }

    private static String getApiKey(Context ctx)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getString(API_KEY, "");
    }

    public static void clearApiKey(Context ctx)
    {
        setApiKey(ctx, "");
    }

    public static void setApiKey(Context ctx, String val)
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor edit = pref.edit();

        edit.putString(API_KEY, val);
        edit.apply();
    }
}
