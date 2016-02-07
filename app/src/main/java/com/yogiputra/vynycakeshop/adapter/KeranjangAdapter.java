package com.yogiputra.vynycakeshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yogiputra.vynycakeshop.R;
import com.yogiputra.vynycakeshop.model.Kue;

import java.util.List;

/**
 * Created by koba on 12/25/15.
 */
public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangHolder> {
private List<Kue> kues;
    private Context context;

    public KeranjangAdapter(Context context, List<Kue> kues){

        this.context=context;
        this.kues=kues;

    }
    @Override
    public KeranjangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan,parent,false);
        KeranjangHolder viewHolder=new KeranjangHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(KeranjangHolder holder, int position) {

            //holder.total_belanj.setText(kues.get(position).getTotal());
            holder.id_pesan=kues.get(position).getId_pesan();
            holder.nama_pesanan.setText(kues.get(position).getNama_kue());

            holder.harga_pesanan.setText(kues.get(position).getHarga());



    }



    @Override
    public int getItemCount() {
        return (null != kues ? kues.size() : 0);
    }


}
