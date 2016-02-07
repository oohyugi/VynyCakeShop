package com.yogiputra.vynycakeshop.network;

import com.squareup.okhttp.RequestBody;
import com.yogiputra.vynycakeshop.model.Kue;
import com.yogiputra.vynycakeshop.model.NewUser;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by koba on 12/16/15.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("daftaruser.php")
    Call<NewUser> daftar(@Field("nama") String nama,
                        @Field("username") String username,
                        @Field("password")String password,
                        @Field("alamat") String alamat,
                        @Field("nohp") String nohp
    );
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<NewUser> login (@Field("username") String username,
                        @Field("password") String password);

    //mengambil json kue berdasarkan id
    @GET("kue.php")
    Call<List<Kue>> getData(@Query("id_kat") Integer id_kat);

    @FormUrlEncoded
    @POST("keranjang.php")
    Call<Kue> keranjang(@Field("id_user") String id_user,
                        @Field("id_kue") String id_kue
    );
    @FormUrlEncoded
    @POST("beli.php")
    Call<Kue> bayar(@Field("id_user") String id_user,
                        @Field("total") String total
    );
    //kuekustom
    @Multipart()
    @POST("krjgambar.php")
    Call<Kue> upload(
                              @Part("id_user") Integer id_user,
                              @Part("id_kue") Integer id_kue,
                              @Part("attachment\"; filename=\"image.jpg\" ") RequestBody file
    );



    @GET("detailpesanan.php")
    Call<List<Kue>> getPesanan(@Query("id_user") Integer id_user);
    @GET("total.php")
    Call<Kue> getTotal(@Query("id_user") Integer id_user);

    @GET("hapus.php")
    Call<Kue> getHapus(@Query("id_pesan") Integer id_pesan);
}
