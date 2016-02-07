package com.yogiputra.vynycakeshop;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;
import com.yogiputra.vynycakeshop.model.Kue;
import com.yogiputra.vynycakeshop.network.RestsClient;
import com.yogiputra.vynycakeshop.shared.SessionManager;

import java.io.File;
import java.util.HashMap;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailActivity extends AppCompatActivity {

    TextView tvNama,tvHarga,tvDesc;
    ImageView gambar;
    String namakue,harga,desc,gamba,id_kue,id_user,id_kat;
SessionManager sessionManager;
Button beli;
    Button upload;
    Toolbar toolbar;
    ProgressDialog pd;
    Context context=this;
    String path		= "";
    ImageView tampil;
    Dialog dialog;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private Uri urlGambar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getBaseContext(), android.R.color.transparent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        collapsingToolbarLayout.setTitle("Detail Kue");
        sessionManager=new SessionManager(this);
        HashMap<String,String> user= sessionManager.getUserId();
        id_user=user.get(SessionManager.KEY_ID_USER);

        tvNama=(TextView)findViewById(R.id.tvNama);
        tvHarga=(TextView)findViewById(R.id.tvHaga);
        tvDesc=(TextView)findViewById(R.id.tvKet);
        gambar=(ImageView)findViewById(R.id.dGambar);
        beli=(Button)findViewById(R.id.btBeli);
        Bundle ambil= getIntent().getExtras();
        id_kat=ambil.getString("id_kat");
        id_kue= ambil.getString("id_kue");
        namakue=ambil.getString("nama_kue");
        harga=ambil.getString("harga");
        desc=ambil.getString("desc");
        gamba=ambil.getString("gambar");


        tvNama.setText(namakue);
        tvHarga.setText(harga);
        tvDesc.setText(desc);
        Picasso.with(this)
                .load(gamba)
                .into(gambar);

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id_kat.equals("2")){
                    ambilGambar();



                }else {
                    tampilLoading();
                    Call<Kue> beli= RestsClient.getRestClients().keranjang(id_user,id_kue);
                    beli.enqueue(new Callback<Kue>() {
                        @Override
                        public void onResponse(Response<Kue> response, Retrofit retrofit) {
                            pd.cancel();

                            if (response.body().getMessage().equals("Sukses")) {
                                Snackbar.make(collapsingToolbarLayout, "Berhasil Menambahkan", Snackbar.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Throwable t) {
                            pd.cancel();
                            Toast.makeText(getApplicationContext(), "Error tidak diketahui !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    private void tampilUploadGambar() {
        final String [] items           = new String [] {"From Camera", "From SD Card"};
        ArrayAdapter<String> adapter  = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder     = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {

                    Intent intent 	 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file		 = new File(Environment.getExternalStorageDirectory(),
                            "img_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    urlGambar = Uri.fromFile(file);

                    try {
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, urlGambar);
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.cancel();


                } else {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Pilih Aplikasi"), PICK_FROM_FILE);
                }
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ambilGambar() {
        dialog= new Dialog(context);
        dialog.setContentView(R.layout.upload_gambar);
        tampil=(ImageView)dialog.findViewById(R.id.gambarup);
        Button cari = (Button)dialog.findViewById(R.id.ambilGambar);
  upload=(Button)dialog.findViewById(R.id.btUpload);





        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                tampilUploadGambar();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tampilLoading();
                if (path == null) {
                    Toast.makeText(context, "Silakan pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {


                    RequestBody file = RequestBody.create(MediaType.parse("image/*"), new File(path));
                    Call<Kue> reg = RestsClient.getRestClients().upload(Integer.parseInt(id_user), Integer.parseInt(id_kue), file);
                    reg.enqueue(new Callback<Kue>() {
                        @Override
                        public void onResponse(Response<Kue> response, Retrofit retrofit) {
                            Toast.makeText(getApplicationContext(), String.valueOf(response.body().getResult()), Toast.LENGTH_SHORT).show();
pd.dismiss();
                        }

                        @Override
                        public void onFailure(Throwable t) {
pd.dismiss();
                        }
                    });
                }
            }
        });
        dialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        Bitmap bitmap 	= null;

        ambilGambar();
        if (requestCode == PICK_FROM_FILE) {

            urlGambar = data.getData();
            path = getRealPath(urlGambar);

            if (path == null)
            {
                path = urlGambar.getPath();
            }
            else
            {
                bitmap 	= BitmapFactory.decodeFile(path);
            }
        }
        else
        {
            ambilGambar();
            path	= urlGambar.getPath();
            tampil.setImageBitmap(bitmap);
            bitmap  = BitmapFactory.decodeFile(path);
        }

        Toast.makeText(this, path,Toast.LENGTH_SHORT).show();
        tampil.setImageBitmap(bitmap);
    }

    private String getRealPath(Uri urlGambar) {
        String path = null;
        String[] images_data = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(urlGambar, images_data, null, null, null);
        if(cursor.moveToFirst())
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }






    private void tampilLoading() {
         pd= new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage("Please Wait..");
        pd.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();

        if (i==android.R.id.home){
            onBackPressed();
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
