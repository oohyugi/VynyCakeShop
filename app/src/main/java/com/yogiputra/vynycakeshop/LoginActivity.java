package com.yogiputra.vynycakeshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.yogiputra.vynycakeshop.model.NewUser;
import com.yogiputra.vynycakeshop.network.RestsClient;
import com.yogiputra.vynycakeshop.shared.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    String varUser,varPass;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private ProgressDialog pdialog;
    
SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        session= new SessionManager(getApplicationContext());
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        mPasswordView = (EditText) findViewById(R.id.password);

        pdialog=new ProgressDialog(this);
        pdialog.setIndeterminate(true);

        pdialog.setMessage("Tunggu Sebentar...");
        Button mDaftar=(Button)findViewById(R.id.email_sign_up_button);
        mDaftar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,DaftarActivity.class));
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin( view);
            }
        });


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(View view) {


        varUser=mEmailView.getText().toString();
        varPass=mPasswordView.getText().toString();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            pdialog.show();
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            Call<NewUser>login= RestsClient.getRestClients().login(varUser, varPass);
            login.enqueue(new Callback<NewUser>() {
                @Override
                public void onResponse(Response<NewUser> response, Retrofit retrofit) {
                    pdialog.dismiss();
                    String nama,id_user,alamat,nohp;

                    if (response.body().getCode()==0){

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        nama= response.body().getNama();
                       alamat= response.body().getAlamat();
                        nohp=response.body().getNohp();
                        id_user=String.valueOf(response.body().getId_user());
                        Log.d("User",nama+"/n"+alamat+"/n"+nohp);
                        session.createLoginSession(nama,alamat,id_user,nohp);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(),"ERROR !!", Toast.LENGTH_LONG).show();
pdialog.cancel();
                }
            });



        }
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

}

