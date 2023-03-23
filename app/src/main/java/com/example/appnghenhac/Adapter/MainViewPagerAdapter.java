package com.example.appnghenhac.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrayFragment = new ArrayList<>();
    private ArrayList<String> arrtittle = new ArrayList<>();
    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //TRA VE VI TRI FRAGMENT//
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayFragment.get(position);
    }

    //TRA VE MANG FRAGMENT//
    @Override
    public int getCount() {
        return arrayFragment.size();
    }

    //ADD CAC FRAGMENT VAO MAN HINH//
    public void addFragment(Fragment fragment , String title){
        arrayFragment.add(fragment);
        arrtittle.add(title);
    }

    //ADD TEN CAC FRAGMENT//
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrtittle.get(position);
    }
}
