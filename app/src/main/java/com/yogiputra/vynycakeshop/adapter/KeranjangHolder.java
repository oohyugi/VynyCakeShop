package com.yogiputra.vynycakeshop.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yogiputra.vynycakeshop.R;
import com.yogiputra.vynycakeshop.app.KueApplication;
import com.yogiputra.vynycakeshop.model.Kue;
import com.yogiputra.vynycakeshop.network.RestsClient;

import java.util.Observable;
import java.util.Observer;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by koba on 12/26/15.
 */
public class KeranjangHolder extends RecyclerView.ViewHolder implements Observer {
    TextView nama_pesanan;
    TextView harga_pesanan;
    TextView total_belanj;
    KueApplication application;
    ImageButton hapus;
    Button konfirmasi;
    int id_pesan;

    ProgressDialog pd;

    public KeranjangHolder(View view) {
        super(view);
        application=(KueApplication)view.getContext().getApplicationContext();
        application.getKueObserver().addObserver(KeranjangHolder.this);
        nama_pesanan=(TextView)view.findViewById(R.id.nama_pesanan);
        harga_pesanan=(TextView)view.findViewById(R.id.harga_pesanan);
        total_belanj=(TextView)view.findViewById(R.id.total);
        konfirmasi=(Button)view.findViewById(R.id.konfirmasi);
        hapus=(ImageButton)view.findViewById(R.id.hapus);


        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showDialog(view);
            }
        });

    }

    private void showDialog(final View view) {
        final AlertDialog.Builder conf= new AlertDialog.Builder(view.getContext());
        conf.setTitle("");
        conf.setMessage("Ada yakin???");
        conf.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tampilLoading(view);
                Call<Kue>call= RestsClient.getRestClients().getHapus(id_pesan);
                call.enqueue(new Callback<Kue>() {
                    @Override
                    public void onResponse(Response<Kue> response, Retrofit retrofit) {

                            Toast.makeText(view.getContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
application.getKueObserver().refresh();

                            pd.dismiss();


                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(view.getContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }

    private void tampilLoading(View view) {

            pd= new ProgressDialog(view.getContext());
            pd.setIndeterminate(true);
              pd.setCancelable(false);
            pd.setMessage("Memasukan kedalam Keranjang..");
            pd.show();
        }

    @Override
    public void update(Observable observable, Object o) {

    }
}

