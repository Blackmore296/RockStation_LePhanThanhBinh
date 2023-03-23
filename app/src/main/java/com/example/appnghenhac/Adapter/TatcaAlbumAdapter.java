package com.example.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.Activity.DanhsachbaihatActivity;
import com.example.appnghenhac.Model.Album;
import com.example.appnghenhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TatcaAlbumAdapter extends RecyclerView.Adapter<TatcaAlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> albumArrayList;

    //CONSTRUCTOR TAT CA ALBUM//
    public TatcaAlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    //GAN DONGTATCAALBUM//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_tat_ca_album,parent,false);
        return new ViewHolder(view);
    }

    //LUU VI TRI ALBUM DANG NGHE VA SET HINHANH,CASI,BAIHAT LEN MAN HINH//
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        Picasso.get().load(album.getHinhalbum()).into(holder.imgtatcaalbum);
        holder.txttatcaalbum.setText(album.getTenalbum());
        holder.txtcasialbum.setText(album.getTencasialbum());

    }

    //TRA VE MANG CUA TAT CA ALBUM//
    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    //CODE XU LY KHI NHAN VAO//
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgtatcaalbum;
        TextView txttatcaalbum,txtcasialbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgtatcaalbum = itemView.findViewById(R.id.imageviewdanhsachalbum);
            txttatcaalbum = itemView.findViewById(R.id.textviewdanhsachalbum);
            txtcasialbum = itemView.findViewById(R.id.textviewdanhsachtencasi);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idalbum",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
