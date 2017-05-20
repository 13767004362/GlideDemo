package com.xingen.glidedemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xingen.glidedemo.adapter.ImageListAdapter;
import com.xingen.glidedemo.fragment.ImageListFragment;
import com.xingen.glidedemo.fragment.SingleImageFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //默认加载
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content_layout, new SingleImageFragment()).commitAllowingStateLoss();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_imageview) {
            fragmentTransaction.replace(R.id.main_content_layout, new SingleImageFragment()).commitAllowingStateLoss();
        } else if (id == R.id.nav_recyclerview) {
            fragmentTransaction.replace(R.id.main_content_layout, ImageListFragment.newInstance(createBundle(ImageListAdapter.NORMAL))).commitAllowingStateLoss();
        } else if (id == R.id.nav_transformation) {
            fragmentTransaction.replace(R.id.main_content_layout, ImageListFragment.newInstance(createBundle(ImageListAdapter.TRANSFORMATION))).commitAllowingStateLoss();
        } else if (id == R.id.nav_imageviewtarget) {
            fragmentTransaction.replace(R.id.main_content_layout, ImageListFragment.newInstance(createBundle(ImageListAdapter.IMAGEVIEWTARGET))).commitAllowingStateLoss();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Bundle createBundle(int modle) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImageListFragment.MODEL, modle);
        return bundle;
    }
}
