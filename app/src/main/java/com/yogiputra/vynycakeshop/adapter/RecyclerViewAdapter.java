package com.yogiputra.vynycakeshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.yogiputra.vynycakeshop.R;
import com.yogiputra.vynycakeshop.model.Kue;

import java.util.List;

/**
 * Created by koba on 12/25/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
private List<Kue> kues;
    private Context context;

    public RecyclerViewAdapter(Context context,List<Kue> kues){

        this.context=context;
        this.kues=kues;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        RecyclerViewHolder viewHolder=new RecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.id_kat=kues.get(position).getId_kat();
        holder.id_kue=kues.get(position).getId_kue();
        holder.gamba=kues.get(position).getGambar();
       holder.desc= kues.get(position).getDesc();
        holder.namaKue.setText(kues.get(position).getNama_kue());
        holder.harga.setText(kues.get(position).getHarga());

      Picasso.with(context).load(kues.get(position).getGambar()).into(holder.gambar);


    }

    @Override
    public int getItemCount() {
        return this.kues.size();
    }


}
