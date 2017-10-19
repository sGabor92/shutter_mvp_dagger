package hu.webandmore.shutter_mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import hu.webandmore.shutter_mvp.R;
import hu.webandmore.shutter_mvp.api.ServiceGenerator;

public class TokenStorage {

    private String token;
    private int uid = 0;
    private String ip = "";


    public TokenStorage(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        token = sharedPref.getString(
                ctx.getString(R.string.preference_token_key), "");
        uid = sharedPref.getInt(
                ctx.getString(R.string.preference_uid_key), 0);
        ip = sharedPref.getString(
                ctx.getString(R.string.preferences_ip_key), "");


    }

    public boolean hasToken(Context ctx) {
        boolean hasit = !token.equals("");
        if (hasit) {
            ServiceGenerator.setApiKey(ctx, token);
        }
        return hasit;
    }

    public String getToken() {
        return token;
    }

    public TokenStorage setToken(Context ctx, String token) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.preference_token_key), token);
        editor.apply();
        this.token = token;
        return this;
    }

    public TokenStorage clear(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.preference_token_key), "");
        editor.apply();
        return this;
    }

    public String getIp() { return ip; }

    public int getUid()
    {
        return uid;
    }

    public TokenStorage setIp(Context ctx, String ip)
    {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.preferences_ip_key), ip);
        editor.commit();
        this.ip = ip;
        return this;
    }

    public TokenStorage setUid(Context ctx, int uid)
    {
        SharedPreferences sharedPref = ctx.getSharedPreferences(
                ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ctx.getString(R.string.preference_uid_key), uid);
        editor.commit();
        this.uid = uid;
        return this;
    }

}
