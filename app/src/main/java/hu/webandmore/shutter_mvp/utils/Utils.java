package hu.webandmore.shutter_mvp.utils;

import android.content.Context;
import android.content.Intent;

import hu.webandmore.shutter_mvp.api.ServiceGenerator;
import hu.webandmore.shutter_mvp.ui.login.LoginActivity;

public class Utils {

    public static void userLogout(Context context) {
        PrefUtils.saveToPrefs(
                context,
                PrefUtils.PREFS_LOGIN_USERNAME_KEY,
                "");
        PrefUtils.saveToPrefs(
                context,
                PrefUtils.PREFS_LOGIN_PASSWORD_KEY,
                "");

        ServiceGenerator.clearApiKey(context);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("logout", true);
        context.startActivity(intent);
    }

}
