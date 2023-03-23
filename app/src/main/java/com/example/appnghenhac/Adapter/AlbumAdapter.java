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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> mangalbum;

    //CONSTRUCTOR ADAPTER ALBUM//
    public AlbumAdapter(Context context, ArrayList<Album> mangalbum) {
        this.context = context;
        this.mangalbum = mangalbum;
    }

    //GAN DONG_ALBUM//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new ViewHolder(view);
    }

    //HUNG DU LIEU TRA VE CUA ALBUM//
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = mangalbum.get(position);
        holder.txtcasialbum.setText(album.getTencasialbum());
        holder.txttenalbum.setText(album.getTenalbum());
        Picasso.get().load(album.getHinhalbum()).into(holder.imgalbum);

    }

    //LAY VE MANG CUA ALBUM//
    @Override
    public int getItemCount() {
        return mangalbum.size();
    }

    //CODE XU LY KHI NHAN VAO//
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgalbum;
        TextView txttenalbum,txtcasialbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgalbum = itemView.findViewById(R.id.imagealbum);
            txttenalbum = itemView.findViewById(R.id.textviewtenalbum);
            txtcasialbum = itemView.findViewById(R.id.textviewtencasialbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idalbum",mangalbum.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }
}
