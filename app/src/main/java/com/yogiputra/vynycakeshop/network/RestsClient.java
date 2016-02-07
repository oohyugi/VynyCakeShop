package com.yogiputra.vynycakeshop.network;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okio.BufferedSource;
import okio.Okio;
import retrofit.Converter;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by Kucing Imut on 3/6/15.
 */
public class RestsClient {


    private static OkHttpClient okhttpklien;

    private static ApiService apis;



    static {

        if (okhttpklien == null) {

            okhttpklien = new OkHttpClient();
            okhttpklien.setConnectTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setWriteTimeout(60, TimeUnit.SECONDS);
            okhttpklien.setReadTimeout(60, TimeUnit.SECONDS);

        }

    }


    public static ApiService getRestClients() {


        if (apis == null) {

            Log.w("API NULL", "API NULL, INIT LAGI");

            Retrofit.Builder retrobuilder = new Retrofit.Builder();
            retrobuilder.baseUrl(URL.LINK).addConverterFactory(GsonConverterFactory.create());
            retrobuilder.client(okhttpklien);



            Retrofit retrofits = retrobuilder.build();
            apis = retrofits.create(ApiService.class);

        }


        return apis;
    }














}




















