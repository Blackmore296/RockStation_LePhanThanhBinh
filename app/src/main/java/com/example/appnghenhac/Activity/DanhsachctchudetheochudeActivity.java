package com.example.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appnghenhac.Adapter.DanhsachctchudetheochudeAdapter;
import com.example.appnghenhac.Model.CTChuDe;
import com.example.appnghenhac.Model.ChuDe;
import com.example.appnghenhac.R;
import com.example.appnghenhac.Service.APIService;
import com.example.appnghenhac.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachctchudetheochudeActivity extends AppCompatActivity {
    ChuDe chuDe;
    RecyclerView recyclerViewctchudetheochude;
    Toolbar toolbartheloaitheochude;
    DanhsachctchudetheochudeAdapter danhsachctchudetheochudeAdapter ;

    //KHOI TAO//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachctchudetheochude);
        GetIntent();
        init();
        GetData();
    }

    //LAY DU LIEU CT_CHUDE THEO CHUDE//
    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<CTChuDe>> callback = dataservice.GetDanhsachctchudetheochude(chuDe.getIdchude());
        callback.enqueue(new Callback<List<CTChuDe>>() {
            @Override
            public void onResponse(Call<List<CTChuDe>> call, Response<List<CTChuDe>> response) {
                ArrayList<CTChuDe> mangctchude = (ArrayList<CTChuDe>) response.body();
                danhsachctchudetheochudeAdapter = new DanhsachctchudetheochudeAdapter(DanhsachctchudetheochudeActivity.this,mangctchude);
                recyclerViewctchudetheochude.setLayoutManager(new GridLayoutManager(DanhsachctchudetheochudeActivity.this,2));
                recyclerViewctchudetheochude.setAdapter(danhsachctchudetheochudeAdapter);

            }
            @Override
            public void onFailure(Call<List<CTChuDe>> call, Throwable t) {

            }
        });
    }

    //DESIGN TOOLBAR//
    private void init() {
        recyclerViewctchudetheochude = this.<RecyclerView>findViewById(R.id.recycleviewctchudetheochude);
        toolbartheloaitheochude = this.<Toolbar>findViewById(R.id.toolbarctchudetheochude);
        setSupportActionBar(toolbartheloaitheochude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenchude());
        toolbartheloaitheochude.setTitleTextColor(getResources().getColor(R.color.white));
        toolbartheloaitheochude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //KHOI TAO INTENT//
    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}