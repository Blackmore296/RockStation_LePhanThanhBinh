package com.example.appnghenhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnghenhac.Activity.PlaynhacActivity;
import com.example.appnghenhac.Model.BaiHat;
import com.example.appnghenhac.R;
import com.example.appnghenhac.Service.APIService;
import com.example.appnghenhac.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> mangbaihat;

    //CONSTRUCTOR SEARCH BAI HAT//
    public SearchBaiHatAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    //GAN DONGSEARCHBAIHAT//
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dong_search_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    //LUU VI TRI VA SET HINHANH,CASI,BAIHAT LEN MAN HINH//
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = mangbaihat.get(position);
        holder.txtTenBaiHat.setText(baiHat.getTenbaihat());
        holder.txtCaSi.setText(baiHat.getCasi());
        Picasso.get().load(baiHat.getHinhbaihat()).into(holder.imageViewBaiHat);

    }

    //TRA VE MANG SEARCH BAI HAT//
    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    //CODE XU LY KHI NHAN VAO//
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenBaiHat,txtCaSi;
        ImageView imageViewBaiHat, imageViewLuotThich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenBaiHat = itemView.findViewById(R.id.textviewsearchtenbaihat);
            txtCaSi = itemView.findViewById(R.id.textviewsearchtencasi);
            imageViewBaiHat = itemView.findViewById(R.id.imageviewsearchbaihat);
            imageViewLuotThich = itemView.findViewById(R.id.imageviewsearchluotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaynhacActivity.class);
                    intent.putExtra("Song",mangbaihat.get(getPosition()));
                    context.startActivity(intent);

                }
            });
            imageViewLuotThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewLuotThich.setImageResource(R.drawable.iconloved);
                    Dataservice dataService = APIService.getService();
                    Call<String> callBack = dataService.GetUpdateLuotthich("1",mangbaihat.get(getPosition()).getIdbaihat());
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketQua = response.body();
                            if(ketQua.equals("Success")){
                                Toast.makeText(context,"Liked",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imageViewLuotThich.setEnabled(false);
                }
            });
        }
    }

}
