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
import com.example.appnghenhac.Model.CTChuDe;
import com.example.appnghenhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachctchudetheochudeAdapter extends RecyclerView.Adapter<DanhsachctchudetheochudeAdapter.ViewHolder>{
    Context context;
    ArrayList<CTChuDe> mangctchude;

    //CONSTRUCTOR DANH SACH CT_CHUDE THEO CHUDE//
    public DanhsachctchudetheochudeAdapter(Context context, ArrayList<CTChuDe> mangctchude) {
        this.context = context;
        this.mangctchude = mangctchude;
    }

    //GAN DONG CT_CHUDETHEOCHUDE//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_ctchude_theo_chude,parent,false);
        return new ViewHolder(view);
    }

    //LUU LAI VI TRI CT_CHUDE DANG NGHE VA SET HINH,TEN CT_CHUDE//
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CTChuDe ctChuDe = mangctchude.get(position);
        Picasso.get().load(ctChuDe.getHinhctchude()).into(holder.imghinhnen);
        holder.tenctchude.setText(ctChuDe.getTenctchude());
    }

    //TRA VE MANG CT_CHUDE THEO CHUDE//
    @Override
    public int getItemCount() {
        return mangctchude.size();
    }

    //CODE XU LY KHI NHAN VAO//
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imghinhnen;
        TextView tenctchude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imghinhnen = itemView.findViewById(R.id.imageviewctchudetheochude);
            tenctchude = itemView.findViewById(R.id.textviewtenctchudetheochude);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idctchude",mangctchude.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
