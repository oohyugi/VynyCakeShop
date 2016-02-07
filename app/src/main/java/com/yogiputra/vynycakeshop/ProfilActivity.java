package com.yogiputra.vynycakeshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yogiputra.vynycakeshop.shared.SessionManager;

import java.util.HashMap;

/**
 * Created by koba on 12/17/15.
 */
public class ProfilActivity extends AppCompatActivity {
String nama,alamat,nohp;
    TextView tvNama,tvAlamat,tvNohp;
    SessionManager session;
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.profil);
        session= new SessionManager(this);
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        tvNama=(TextView)findViewById(R.id.nama);
        tvAlamat=(TextView)findViewById(R.id.alamat);
        tvNohp=(TextView)findViewById(R.id.nohp);
        Button logout=(Button)findViewById(R.id.btLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });
        HashMap<String,String>user= session.getUserDetails();
        nama=user.get(SessionManager.KEY_NAME);
        alamat=user.get(SessionManager.KEY_AlAMAT);
        nohp=user.get(SessionManager.KEY_NOHP);

        tvNama.setText(nama);
        tvAlamat.setText(alamat);
        tvNohp.setText(nohp);
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
