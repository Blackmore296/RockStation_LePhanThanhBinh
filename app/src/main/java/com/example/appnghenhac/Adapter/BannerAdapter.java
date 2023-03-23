package com.example.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appnghenhac.Activity.DanhsachbaihatActivity;
import com.example.appnghenhac.Model.QuangCao;
import com.example.appnghenhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrayListbanner;

    //CONSTRUCTOR BANNER QUANGCAO//
    public BannerAdapter(Context context, ArrayList<QuangCao> arrayListbanner) {
        this.context = context;
        this.arrayListbanner = arrayListbanner;
    }

    //TRA VE MANG BANNER QUANGCAO//
    @Override
    public int getCount() {
        return arrayListbanner.size();
    }

    //GAN DOI TUONG OBJECT TRA VE//
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    //GAN DONG BANNER//
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dongbanner,null);

        ImageView imgBackgroundBanner = view.findViewById(R.id.imagebackgroundbanner);
        ImageView imgSongBanner = view.findViewById(R.id.imageviewbanner);
        TextView txtTitleSongBanner = view.findViewById(R.id.textviewtitlebannerbaihat);
        TextView txtNoiDung = view.findViewById(R.id.textviewnoidung);

        Picasso.get().load(arrayListbanner.get(position).getHinhanh()).into(imgBackgroundBanner);
        Picasso.get().load(arrayListbanner.get(position).getHinhbaihat()).into(imgSongBanner);
        txtTitleSongBanner.setText(arrayListbanner.get(position).getTenbaihat());
        txtNoiDung.setText(arrayListbanner.get(position).getNoidung());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                intent.putExtra("Banner",arrayListbanner.get(position));
                context.startActivity(intent);

            }
        });
        container.addView(view);
        return view;
    }

    //HUY OBJECT//
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


