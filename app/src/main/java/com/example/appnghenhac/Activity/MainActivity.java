package com.example.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.appnghenhac.Adapter.MainViewPagerAdapter;
import com.example.appnghenhac.Background.ForegroundServiceControl;
import com.example.appnghenhac.Fragment.Fragment_Tim_Kiem;
import com.example.appnghenhac.Fragment.Fragment_Trang_Chu;
import com.example.appnghenhac.Model.BaiHat;
import com.example.appnghenhac.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private  long backPressedTime;
    private Toast mToast;

    //KHOI TAO//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        init();

    }

    //CODE NUT BACK CUA DEVICE//
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            mToast = Toast.makeText(MainActivity.this,"Press back again to exit",Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    //NGUNG SERVICE CHAY NGAM//
    public void StopService(){
        Intent intent = new Intent(this, ForegroundServiceControl.class);
        stopService(intent);
    }

    //DESIGN MAIN ACTIVITY//
    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Trang_Chu(),"HOME");
        mainViewPagerAdapter.addFragment(new Fragment_Tim_Kiem(),"SEARCH");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontimkiem);

    }

    //ANH XA//
    private void Anhxa() {
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
    }

    //NGUNG SERVICE CHAY NGAM//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopService();
    }
}