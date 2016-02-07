package com.yogiputra.vynycakeshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yogiputra.vynycakeshop.adapter.KeranjangAdapter;
import com.yogiputra.vynycakeshop.app.KueApplication;
import com.yogiputra.vynycakeshop.model.Kue;
import com.yogiputra.vynycakeshop.model.KueObserver;
import com.yogiputra.vynycakeshop.network.RestsClient;
import com.yogiputra.vynycakeshop.shared.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Keranjang extends AppCompatActivity implements Observer {
SwipeRefreshLayout swipeRefreshLayout;
    KeranjangAdapter adapter;
    SessionManager sessionManager;

    String id_user;
    String id_pesan;
    String totalblj;
    List<Kue>kues;
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    LinearLayout lineError;
    TextView tvTotal;
    KueApplication application;
    Button tambahPesan,bayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
      Toolbar  toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        application=(KueApplication)getApplication();
        application.getKueObserver().addObserver(Keranjang.this);


        lineError=(LinearLayout)findViewById(R.id.lineError);
        layoutManager = new LinearLayoutManager(Keranjang.this);
rv=(RecyclerView)findViewById(R.id.rv_item);
        tvTotal=(TextView)findViewById(R.id.total);
        rv.setLayoutManager(layoutManager);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefres);
        tambahPesan=(Button)findViewById(R.id.tambah_pesanan);
        bayar=(Button)findViewById(R.id.konfirmasi);
        tambahPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Keranjang.this, MainActivity.class));
                finish();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefreshLayout.setRefreshing(true);
                bayarTagihan();
            }
        });
        sessionManager=new SessionManager(this);
        HashMap<String,String> user= sessionManager.getUserId();
        id_user=user.get(SessionManager.KEY_ID_USER);


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

    tampilData();







            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                tampilData();
            }
        });
    }




    private void tampilData() {


        swipeRefreshLayout.setRefreshing(false);
        Call<List<Kue>> call = RestsClient.getRestClients().getPesanan(Integer.parseInt(id_user));
        call.enqueue(new Callback<List<Kue>>() {

            @Override
            public void onResponse(Response<List<Kue>> response, Retrofit retrofit) {
                kues = response.body();



                adapter = new KeranjangAdapter(Keranjang.this, kues);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               if (adapter.getItemCount() >0) {


lineError.setVisibility(View.INVISIBLE);

                } else {


                    lineError.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        final Call<Kue> total = RestsClient.getRestClients().getTotal(Integer.parseInt(id_user));
        total.enqueue(new Callback<Kue>() {
            @Override
            public void onResponse(Response<Kue> response, Retrofit retrofit) {
                  //  Toast.makeText(getApplicationContext(),String.valueOf(response.body().getTotal()),Toast.LENGTH_SHORT).show();
totalblj=response.body().getTotal();
                tvTotal.setText(totalblj);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


    }
    private void bayarTagihan() {
Call<Kue> bayar= RestsClient.getRestClients().bayar(id_user, totalblj);

        bayar.enqueue(new Callback<Kue>() {
            @Override
            public void onResponse(Response<Kue> response, Retrofit retrofit) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.body().getMessage().equals("sukses")){

                    tampilPesan();
                }else {
                    Toast.makeText(getApplicationContext(),String.valueOf(response.body().getMessage()),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void tampilPesan() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Pesanan sedang diproses.\n" +
                "Siapkan uang pas untuk membayar tagihan"
                );
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();

        if (i==android.R.id.home){
            onBackPressed();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o.equals(KueObserver.NEED_TO_REFRESH)){
tampilData();
        }
    }
}
