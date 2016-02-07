package com.yogiputra.vynycakeshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.yogiputra.vynycakeshop.model.NewUser;
import com.yogiputra.vynycakeshop.network.RestsClient;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class DaftarActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView,mNama,mNoktp,mNohp,mAlamat;
    String varUser,varPass,varAlamat,varNohp,varNoktp,varNama;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private ProgressDialog pdialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
    mNama=(EditText)findViewById(R.id.nama);

        mNohp=(EditText)findViewById(R.id.nohp);
        mAlamat=(EditText)findViewById(R.id.alamat);

        mPasswordView = (EditText) findViewById(R.id.password);



        pdialog=new ProgressDialog(this);
        pdialog.setIndeterminate(true);

        pdialog.setMessage("Tunggu Sebentar...");

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        varAlamat=mAlamat.getText().toString();
        varNama=mNama.getText().toString();
        varNohp=mNohp.getText().toString();

        varUser=mEmailView.getText().toString();
        varPass=mPasswordView.getText().toString();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(varUser) && !isPasswordValid(varPass)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(varUser)) {
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

            Call<NewUser>kirim= RestsClient.getRestClients().daftar(varNama, varUser, varPass,
                    varAlamat, varNohp);
            kirim.enqueue(new Callback<NewUser>() {
                @Override
                public void onResponse(Response<NewUser> response, Retrofit retrofit) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    pdialog.cancel();
                  if (response.body().getMessage().equals("Registration successfull")){
                        startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                    }



                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Pendaftaran Gagal", Toast.LENGTH_SHORT).show();

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

