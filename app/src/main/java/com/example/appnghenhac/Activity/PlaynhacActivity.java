package com.example.appnghenhac.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.appnghenhac.Adapter.ViewPagerPlaylistNhacAdapter;
import com.example.appnghenhac.Background.ForegroundServiceControl;
import com.example.appnghenhac.Fragment.Fragment_Dia_Nhac;
import com.example.appnghenhac.Fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.appnghenhac.Model.BaiHat;
import com.example.appnghenhac.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;




public class PlaynhacActivity extends AppCompatActivity {
    Toolbar toolbarplaynhac;
    TextView textViewTimesong,textViewTotaltimesong;
    SeekBar seekBarsk;
    ImageButton imgplay,imgrepeat,imgnext,imgpre,imgrandom;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    Fragment_Dia_Nhac fragment_dia_nhac;
    public static ViewPagerPlaylistNhacAdapter adapternhac;
    private int dem = 0, position = 0, duration = 0, timeValue = 0, durationToService = 0;
    boolean isplaying;
    boolean repeat = false;
    boolean checkRandom = false;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null){
                isplaying = intent.getBooleanExtra("status_player", false);
                int action = intent.getIntExtra("action_music", 0);
                duration = intent.getIntExtra("duration_music", 0);
                timeValue = intent.getIntExtra("seektomusic", 0);
                position = intent.getIntExtra("position_music", 0);
                seekBarsk.setProgress(timeValue);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                textViewTimesong.setText(simpleDateFormat.format(timeValue));
                handleMusic(action);
                TimeSong();
            }
        }
    };

    //KHOI TAO//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playnhac);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("send_data_to_activity"));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetDataFromIntent();
        init();
        eventClick();
        setViewStart();
        StartService();
    }

    //KHOI TAO SERVICE CHAY NGAM//
    private void StartService() {
        Intent intent =  new Intent(this, ForegroundServiceControl.class);
        if (mangbaihat.size() > 0){
            intent.putExtra("obj_song_baihat", mangbaihat);
        }
        startService(intent);
    }

    //CODE CAC NUT XU LY//
    private void eventClick() {
        imgplay.setOnClickListener(view -> {
            if (isplaying){
                sendActionToService(ForegroundServiceControl.ACTION_PAUSE);
                imgplay.setImageResource(R.drawable.iconplay);
                if (fragment_dia_nhac.objectAnimator!=null){
                    fragment_dia_nhac.objectAnimator.pause();
                }

            }else {
                sendActionToService(ForegroundServiceControl.ACTION_RESUME);
                imgplay.setImageResource(R.drawable.iconpause);
                if (fragment_dia_nhac.objectAnimator!=null){
                    fragment_dia_nhac.objectAnimator.resume();
                }
            }
        });

        imgrepeat.setOnClickListener(this::onClick);
        imgrandom.setOnClickListener(view -> {
            if (!checkRandom){
                if (repeat){
                    repeat = false;
                    imgrandom.setImageResource(R.drawable.iconshuffled);
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                }else {
                    imgrepeat.setImageResource(R.drawable.iconshuffled);
                }
                checkRandom = true;
            }else {
                imgrandom.setImageResource(R.drawable.iconsuffle);
                checkRandom = false;
            }
            sendActionToService(ForegroundServiceControl.ACTION_RANDOM);
        });
        seekBarsk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                durationToService = seekBar.getProgress();
                sendActionToService(ForegroundServiceControl.ACTION_DURATION);
            }
        });
        imgnext.setOnClickListener(view -> sendActionToService(ForegroundServiceControl.ACTION_NEXT));
        imgpre.setOnClickListener(view -> sendActionToService(ForegroundServiceControl.ACTION_PREVIOUS));
        toolbarplaynhac.setNavigationOnClickListener(view -> {
            mangbaihat.clear();
            finish();
        });
    }


    //RESET NEXT MUSIC//
    private void NextMusic(){
        imgplay.setImageResource(R.drawable.iconpause);
        timeValue = 0;
    }

    //DIEU KIEN RESET NEXT MUSIC//
    private void completeNextMusic() {
        if (mangbaihat.size() > 0){
            NextMusic();

        }
    }

    //RESET PREV MUSIC//
    private void PreviousMusic(){
        imgplay.setImageResource(R.drawable.iconpause);
        timeValue = 0;
    }

    //DIEU KIEN PREV MUSIC//
    private void completePreviousMusic() {
        if (mangbaihat.size() > 0){
            PreviousMusic();

        }
    }

    //CODE GIAO DIEN KHI CO SU THAY DOI BAI HAT//
    private void setViewStart(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mangbaihat.size() > 0){
                    setView(mangbaihat.get(position).getHinhbaihat(), mangbaihat.get(position).getTenbaihat(), mangbaihat.get(position).getCasi());
                }else {
                    handler.postDelayed(this, 300);
                }
            }
        }, 500);
    }

    //SET HINHANH,CASI,BAIHAT LEN MAN HINH//
    private void setView(String hinhBaiHat, String tenBaiHat, String tenCaSi){
        fragment_dia_nhac.playNhac(hinhBaiHat);
        Objects.requireNonNull(getSupportActionBar()).setTitle(tenBaiHat);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(tenCaSi);
    }

    //KHOI TAO INTENT//
    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null){
            if (intent.hasExtra("Song")){
                BaiHat baiHat = intent.getParcelableExtra("Song");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("danhsachbaihat")){
                ArrayList<BaiHat> baiHatArrayList= intent.getParcelableArrayListExtra("danhsachbaihat");
                mangbaihat = baiHatArrayList;
            }
        }
    }

    //DESIGN TOOLBAR//
    private void init() {
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        seekBarsk = findViewById(R.id.seekbarsong);
        ViewPager viewPagerplaynhac = findViewById(R.id.viewpagerplaynhac);
        imgrandom = findViewById(R.id.imagebuttonsuffle);
        imgpre= findViewById(R.id.imagebuttonpreview);
        imgplay = findViewById(R.id.imagebuttonplay);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        textViewTotaltimesong= findViewById(R.id.textviewtotaltimesong);
        textViewTimesong = findViewById(R.id.textviewtimesong);
        fragment_dia_nhac = new Fragment_Dia_Nhac();
        fragment_play_danh_sach_bai_hat = new Fragment_Play_Danh_Sach_Bai_Hat();
        adapternhac = new ViewPagerPlaylistNhacAdapter(getSupportFragmentManager());
        adapternhac.AddFragment(fragment_play_danh_sach_bai_hat);
        adapternhac.AddFragment(fragment_dia_nhac);
        viewPagerplaynhac.setAdapter(adapternhac);
        setSupportActionBar(toolbarplaynhac);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        toolbarplaynhac.setSubtitleTextColor(Color.WHITE);
        fragment_dia_nhac = (Fragment_Dia_Nhac) adapternhac.getItem(1);
    }

    //CODE THOI GIAN HIEN THI//
    private void TimeSong() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textViewTotaltimesong.setText(simpleDateFormat.format(duration));
        seekBarsk.setMax(duration);
    }

    //CODE CHUYEN DOI NHAC CUA NOTIFICATION//
    private void handleMusic(int action){
        switch (action){
            case ForegroundServiceControl.ACTION_PAUSE:
                imgplay.setImageResource(R.drawable.iconplay);
                fragment_dia_nhac.objectAnimator.pause();
                break;
            case ForegroundServiceControl.ACTION_RESUME:
                imgplay.setImageResource(R.drawable.iconpause);
                fragment_dia_nhac.objectAnimator.resume();
                break;
            case ForegroundServiceControl.ACTION_NEXT:
                completeNextMusic();
                setViewStart();
                break;
            case ForegroundServiceControl.ACTION_PREVIOUS:
                completePreviousMusic();
                setViewStart();
                break;
        }
    }

    //GUI TIN HIEU CAC NUT VE HAM CHAY NGAM//
    private void sendActionToService(int action){
        Intent intent = new Intent(this, ForegroundServiceControl.class);
        intent.putExtra("action_music_service", action);
        intent.putExtra("duration", durationToService);
        intent.putExtra("repeat_music", repeat);
        intent.putExtra("random_music", checkRandom);
        startService(intent);
    }

    //PHUONG THUC HUY CHUYEN TIN HIEU
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    //SET GIAO DIEN CAC NUT KHI CO SU THAY DOI//
    private void onClick(View view) {
        if (!repeat) {
            if (checkRandom) {
                checkRandom = false;
                imgrepeat.setImageResource(R.drawable.iconsyned);
                imgrandom.setImageResource(R.drawable.iconsuffle);
            } else {
                imgrepeat.setImageResource(R.drawable.iconsyned);
            }
            repeat = true;
        } else {
            imgrepeat.setImageResource(R.drawable.iconrepeat);
            repeat = false;
        }
        sendActionToService(ForegroundServiceControl.ACTION_REPEAT);
    }
}
