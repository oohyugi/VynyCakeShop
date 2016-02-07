package com.yogiputra.vynycakeshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yogiputra.vynycakeshop.DetailActivity;
import com.yogiputra.vynycakeshop.R;
import com.yogiputra.vynycakeshop.model.Kue;

import java.util.List;

/**
 * Created by koba on 12/25/15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView gambar;
    TextView harga;
    TextView namaKue;
    int id_kue;
    int id_kat;
    String desc;
    String gamba;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        namaKue=(TextView)itemView.findViewById(R.id.namaKue);
        harga=(TextView)itemView.findViewById(R.id.harga);
        gambar=(ImageView)itemView.findViewById(R.id.img);

    }

    @Override
    public void onClick(View view) {
        Context context= view.getContext();
      //  Toast.makeText(context,desc,Toast.LENGTH_SHORT).show();
       Intent kirim= new Intent(context, DetailActivity.class);
        kirim.putExtra("id_kat",String.valueOf(id_kat));
        kirim.putExtra("nama_kue",namaKue.getText());
        kirim.putExtra("harga",harga.getText());
        kirim.putExtra("desc",desc);
        kirim.putExtra("gambar", gamba);
        kirim.putExtra("id_kue",String.valueOf(id_kue));
        context.startActivity(kirim);


    }
}
