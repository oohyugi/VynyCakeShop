package com.yogiputra.vynycakeshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yogiputra.vynycakeshop.R;
import com.yogiputra.vynycakeshop.adapter.RecyclerViewAdapter;
import com.yogiputra.vynycakeshop.model.Kue;
import com.yogiputra.vynycakeshop.network.ApiService;
import com.yogiputra.vynycakeshop.network.RestsClient;
import com.yogiputra.vynycakeshop.network.URL;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class KueCustom extends Fragment {



    private RecyclerView rv;

    LinearLayoutManager layoutManager;
    RecyclerViewAdapter adapter;

    List<Kue>kues;
    private SwipeRefreshLayout swipeRefreshLayout;

    public KueCustom(){

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        final View rootView=inflater.inflate(R.layout.kue_grid, container, false);
        rv=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        layoutManager=new GridLayoutManager(getActivity(),2);


        rv.setLayoutManager(layoutManager);
        swipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefres);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                tampilData(rootView);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                tampilData(rootView);
            }
        });






        return rootView;
    }

    private void tampilData(View rootView) {

        swipeRefreshLayout.setRefreshing(false);
        Call<List<Kue>> call= RestsClient.getRestClients().getData(2);
        call.enqueue(new Callback<List<Kue>>() {
            @Override
            public void onResponse(Response<List<Kue>> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    kues  = response.body();
                    adapter= new RecyclerViewAdapter(getActivity(),kues);
                    rv.setAdapter(adapter);


                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });




    }


}
