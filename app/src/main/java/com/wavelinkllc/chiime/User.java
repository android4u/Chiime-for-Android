/**
 * Created by kelvin on 8/9/15.
 */
package com.wavelinkllc.chiime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

interface OnSignInCompleteListener {
    void onSignInComplete(boolean isAuthenticated, boolean isNetworkError);
}

public class User {

    private static final String LOGIN_URL = "http://www.chiime.co/services/login/chiime_android.php";
    private static final String PREFERENCE_NAME = "Chiime";
    private static final String USER_ID_SESSION_KEY = "userId";
    private static final String USERNAME_SESSION_KEY = "username";
    private static final String PASSWORD_SESSION_KEY = "password";

    public static String userId;
    public static String username;
    public static String password;

    public static Editor editor;

    private User() { }

    public static boolean attemptToloadUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        userId = preferences.getString(USER_ID_SESSION_KEY, null);
        username = preferences.getString(USERNAME_SESSION_KEY, null);
        password = preferences.getString(PASSWORD_SESSION_KEY, null);
        return null != userId;
    }

    public static void signInWithCredentials(Context context, String usernameCredential, final String passwordCredential, final OnSignInCompleteListener listener) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        editor = preferences.edit();
        Ion.with(context)
            .load(LOGIN_URL)
            .setBodyParameter("username", usernameCredential)
            .setBodyParameter("password", passwordCredential)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    boolean isAuthenticated = false;
                    boolean isNetworkError = false;
                    if (null == e) {
                        if (result.get("authenticated").getAsBoolean()) {
                            userId = result.get("userId").getAsString();
                            username = result.get("username").getAsString();
                            password = passwordCredential;

                            editor.putString(USER_ID_SESSION_KEY, userId);
                            editor.putString(USERNAME_SESSION_KEY, username);
                            editor.putString(PASSWORD_SESSION_KEY, password);
                            editor.apply();

                            isAuthenticated = true;
                        }
                    } else {
                        isNetworkError = true;
                    }
                    listener.onSignInComplete(isAuthenticated, isNetworkError);
            }
        });
    }

}
