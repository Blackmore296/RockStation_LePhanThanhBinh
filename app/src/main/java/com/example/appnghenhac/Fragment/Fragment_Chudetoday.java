package com.example.appnghenhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.Activity.DanhsachbaihatActivity;
import com.example.appnghenhac.Activity.DanhsachctchudetheochudeActivity;
import com.example.appnghenhac.Activity.DanhsachtatcachudeActivity;
import com.example.appnghenhac.Model.CTChuDe;
import com.example.appnghenhac.Model.ChuDe;
import com.example.appnghenhac.Model.Chudetrongngay;
import com.example.appnghenhac.R;
import com.example.appnghenhac.Service.APIService;
import com.example.appnghenhac.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Chudetoday extends Fragment {
    View view;
    HorizontalScrollView horizontalScrollView;
    ImageView txtxemthemchudetheloai;

    //GAN FRAGMENTCHUDECTCHUDETODAY//
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chude_ctchude_today,container,false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollview);
        txtxemthemchudetheloai = view.findViewById(R.id.textviewxemthem);
        txtxemthemchudetheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DanhsachtatcachudeActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }

    //LAY DU LIEU CHUDETRONGNGAY//
    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<Chudetrongngay> callback = dataservice.Getchudevatheloaitrongngay();
        callback.enqueue(new Callback<Chudetrongngay>() {
            @Override
            public void onResponse(Call<Chudetrongngay> call, Response<Chudetrongngay> response) {
                Chudetrongngay chudetrongngay = response.body();

                final ArrayList<ChuDe> chuDeArrayList = new ArrayList<>();
                chuDeArrayList.addAll(chudetrongngay.getChuDe());

                final ArrayList<CTChuDe> ctChuDeArrayList = new ArrayList<>();
                ctChuDeArrayList.addAll(chudetrongngay.getCTChuDe());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(580, 250);
                layoutParams.setMargins(10, 20, 10, 30);

                for (int i = 0; i < (chuDeArrayList.size()); i++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (chuDeArrayList.get(i).getHinhchude() != null) {
                        Picasso.get().load(chuDeArrayList.get(i).getHinhchude()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), DanhsachctchudetheochudeActivity.class);
                            intent.putExtra("chude",chuDeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });

                }

                for (int j = 0; j < (chuDeArrayList.size()); j++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (ctChuDeArrayList.get(j).getHinhctchude() != null) {
                        Picasso.get().load(ctChuDeArrayList.get(j).getHinhctchude()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);

                    int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), DanhsachbaihatActivity.class);
                            intent.putExtra("idctchude",ctChuDeArrayList.get(finalJ));
                            startActivity(intent);
                        }
                    });

                }
                horizontalScrollView.addView(linearLayout);
            }
            @Override
            public void onFailure(Call<Chudetrongngay> call, Throwable t) {

            }
        });
    }
}

