package hu.webandmore.shutter_mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefUtils {

    public static final String PREFS_LOGIN_USERNAME_KEY = "__USERNAME__";
    public static final String PREFS_LOGIN_PASSWORD_KEY = "__PASSWORD__";
    public static final String PREFS_LOGIN_TOKEN = "__TOKEN__";

    public static void saveToPrefs(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveArrayToPrefs(Context context, String key, ArrayList<Integer> obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        saveToPrefs(context, key, json);
    }

    public static ArrayList<Integer> getArrayFromPrefs(Context context, String key) {
        String src = getFromPrefs(context, key, "");
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        if (src.equals("")) return null;
        else return new Gson().fromJson(src, type);
    }

    public static String getFromPrefs(Context context, String key, String defaultValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
