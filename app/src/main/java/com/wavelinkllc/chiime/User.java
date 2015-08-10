/**
 * Created by kelvin on 8/9/15.
 */
package com.wavelinkllc.chiime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

interface OnSignInCompleteListener {
    void onSignInComplete(boolean isAuthenticated, boolean isNetworkError);
}

public class User {

    private static final String LOGIN_URL = "http://www.chiime.co/services/login/chiime_android.php";
    private static final String PREFERENCE_NAME = "Chiime";
    private static final String USER_ID_KEY = "userId";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String AUTHENTICATED_KEY = "authenticated";

    public static String userId;
    public static String username;
    public static String password;

    public static Editor editor;

    private User() { }

    public static boolean attemptToloadUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        userId = preferences.getString(USER_ID_KEY, null);
        username = preferences.getString(USERNAME_KEY, null);
        password = preferences.getString(PASSWORD_KEY, null);
        return null != userId;
    }

    public static void signInWithCredentials(Context context, String usernameCredential, final String passwordCredential, final OnSignInCompleteListener listener) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, 0);
        editor = preferences.edit();
        Ion.with(context)
            .load(LOGIN_URL)
            .setBodyParameter(USERNAME_KEY, usernameCredential)
            .setBodyParameter(PASSWORD_KEY, passwordCredential)
            .asJsonObject()
            .setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    boolean isAuthenticated = false;
                    boolean isNetworkError = false;
                    if (null == e) {
                        if (result.get(AUTHENTICATED_KEY).getAsBoolean()) {
                            userId = result.get(USER_ID_KEY).getAsString();
                            username = result.get(USERNAME_KEY).getAsString();
                            password = passwordCredential;

                            editor.putString(USER_ID_KEY, userId);
                            editor.putString(USERNAME_KEY, username);
                            editor.putString(PASSWORD_KEY, password);
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
