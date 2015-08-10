package com.wavelinkllc.chiime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.async.future.FutureCallback;

public class SignInActivity extends FragmentActivity implements SignInDialogFragment.SignInDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (User.attemptToloadUser(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Logged in as " + User.username, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Not logged in", Toast.LENGTH_SHORT).show();
        }

        TextView logoTextTextView = (TextView)findViewById(R.id.logo_text);
        logoTextTextView.setTypeface(FontManager.getTypeface(this, "fonts/ArialRoundedBold.ttf"));
    }

    public void showSignInDialog(View view) {
        DialogFragment dialog = new SignInDialogFragment();
        dialog.show(this.getFragmentManager(), "SignInDialogFragment");
    }

    @Override
    public void onSignInDialogSubmitClick(DialogInterface dialogInterface) {
        Dialog dialog = (Dialog) dialogInterface;
        EditText usernameTextBox = (EditText)dialog.findViewById(R.id.username_text_box);
        EditText passwordTextBox = (EditText)dialog.findViewById(R.id.password_text_box);
        User.signInWithCredentials(getApplicationContext(), usernameTextBox.getText().toString(), passwordTextBox.getText().toString(), new OnSignInCompleteListener() {
                    public void onSignInComplete(boolean isAuthenticated, boolean isNetworkError) {
                        if (!isNetworkError) {
                            if (isAuthenticated) {
                                Toast.makeText(getApplicationContext(), "Logged in as " + User.username, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Could not find user. Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Network error.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void signUp(View view) {
        // Do something in response to button click
    }

}
