package com.yogiputra.vynycakeshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.yogiputra.vynycakeshop.adapter.MyPagerAdapter;
import com.yogiputra.vynycakeshop.shared.SessionManager;

public class MainActivity extends AppCompatActivity {
SessionManager session;
    ViewPager viewPager;
    TabLayout tabLayout;
CoordinatorLayout coordinatorLayout;
    CharSequence title[] = {"Cake","Custom Cake"};
    int NumbOftabs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(this);
        session.checkLogin();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coor);
        tabLayout =(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), title, NumbOftabs));
        tabLayout.setupWithViewPager(viewPager);
        /*HashMap<String,String> user= session.getUserDetails();
        String nama= user.get(SessionManager.KEY_NAME);
        String email=user.get(SessionManager.KEY_AlAMAT);
        String id_user= user.get(SessionManager.KEY_ID_USER);
        String nohp=user.get(SessionManager.KEY_NOHP); */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Keranjang.class));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,ProfilActivity.class));
            return true;
        }
if (id== R.id.shoppingCart){

    Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
}
        return super.onOptionsItemSelected(item);
    }
}
