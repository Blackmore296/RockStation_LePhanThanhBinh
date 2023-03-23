package com.example.appnghenhac.Background;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.appnghenhac.Activity.PlaynhacActivity;
import com.example.appnghenhac.Model.BaiHat;
import com.example.appnghenhac.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import static com.example.appnghenhac.Background.ChanelNoti.channel;

public class ForegroundServiceControl extends Service {
    //SET GIA TRI CHO CAC SU KIEN ACTION//
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_NEXT = 3;
    public static final int ACTION_PREVIOUS = 4;
    public static final int ACTION_DURATION = 5;
    public static final int ACTION_REPEAT = 6;
    public static final int ACTION_RANDOM = 7;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying, isRepeat, isRandom;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    private int positionPlayer = 0, duration = 0, seekToTime = 0, curentime = 0;
    private String urlImage;

    //KHOI TAO//
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //LUU VI
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra("obj_song_baihat")) {
                clearArray();
                mangbaihat = intent.getParcelableArrayListExtra("obj_song_baihat");
            }
        }
        assert intent != null;
        if (!intent.hasExtra("action_music_service")){
            CompleteAndStart();
        }
        int actionMusic = intent.getIntExtra("action_music_service", 0);
        seekToTime = intent.getIntExtra("duration", 0);
        isRepeat = intent.getBooleanExtra("repeat_music", false);
        isRandom = intent.getBooleanExtra("random_music", false);
        handleActionMusic(actionMusic);
        return START_NOT_STICKY;
    }

    //CLEAR MANG BAIHAT//
    private void clearArray() {
        positionPlayer = 0;
        mangbaihat.clear();
    }

    //XU LY CAC TRANG THAI NHAC//
    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    pauseMusic(mangbaihat.get(positionPlayer).getTenbaihat(), mangbaihat.get(positionPlayer).getCasi());

                }
                break;
            case ACTION_RESUME:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    resumeMusic(mangbaihat.get(positionPlayer).getTenbaihat(), mangbaihat.get(positionPlayer).getCasi());

                }
                break;
            case ACTION_NEXT:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    nextMusic(mangbaihat.size());
                }
                CompleteAndStart();
                break;
            case ACTION_PREVIOUS:
                if (mangbaihat != null && mangbaihat.size() > 0){
                    previousMusic(mangbaihat.size());
                }
                CompleteAndStart();
                break;
            case ACTION_DURATION:
                mediaPlayer.seekTo(seekToTime);
                break;
        }
    }

    //XU LY START NHAC//
    private void startMusic(String linkBaiHat) {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        new playMP3().onPostExecute(linkBaiHat);
        isPlaying = true;
        duration = mediaPlayer.getDuration();
        sendActonToPlayNhacActivity(ACTION_RESUME);
        sendTimeCurrent();

    }

    //XU LY CHOI TIEP NHAC//
    private void resumeMusic(String tenBaiHat, String tenCaSi) {
        if (mediaPlayer != null && !isPlaying){
            mediaPlayer.start();
            isPlaying = true;
            sendNotificationMedia(tenBaiHat, tenCaSi);
            sendActonToPlayNhacActivity(ACTION_RESUME);
        }
    }

    //XU LY NGUNG NHAC//
    private void pauseMusic(String tenBaiHat, String tenCaSi) {
        if (mediaPlayer != null && isPlaying){
            mediaPlayer.pause();
            isPlaying = false;
            sendNotificationMedia(tenBaiHat, tenCaSi);
            sendActonToPlayNhacActivity(ACTION_PAUSE);
        }
    }

    //XU LY NEXT NHAC//
    private void nextMusic(int sizeArray){
        positionPlayer++;
        if (isRepeat){
            positionPlayer -= 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer >= sizeArray){
            positionPlayer = 0;
        }
        sendActonToPlayNhacActivity(ACTION_NEXT);
    }

    //XU LY PREV NHAC//
    private void previousMusic(int sizeArray){
        positionPlayer--;
        if (isRepeat){
            positionPlayer += 1;
        }else if (isRandom){
            Random random = new Random();
            positionPlayer = random.nextInt(sizeArray);
        }
        if (positionPlayer < 0){
            positionPlayer = sizeArray-1;
        }
        sendActonToPlayNhacActivity(ACTION_PREVIOUS);
    }

    //KHOI TAO CHOI NHAC VA SET HINHANH,CASI,BAIHAT LEN THANH NOTIFICATION//
    private void CompleteAndStart(){
        if (mangbaihat != null && mangbaihat.size() > 0){
            startMusic(mangbaihat.get(positionPlayer).getLinkbaihat());
            urlImage = mangbaihat.get(positionPlayer).getHinhbaihat();
            sendNotificationMedia(mangbaihat.get(positionPlayer).getTenbaihat(), mangbaihat.get(positionPlayer).getCasi());

        }
    }

    //KHOI TAO THANH NOTIFICATION HIEN THI LEN DEVICE//
    private void sendNotificationMedia(String tenBaiHat, String tenCaSi){
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");

        Intent openintent = new Intent(this, PlaynhacActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,openintent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.logo_intro)
                .setContentText("RockStation")
                .setContentTitle(tenBaiHat)
                .setContentText(tenCaSi)
                .setContentIntent(pendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0,1,2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .addAction(R.drawable.iconpreview, "Previous", getPendingIntent(this, ACTION_PREVIOUS));
        if (isPlaying){
            notificationBuilder.addAction(R.drawable.iconpause, "Pause", getPendingIntent(this, ACTION_PAUSE));

        }else {
            notificationBuilder.addAction(R.drawable.iconplay, "Pause", getPendingIntent(this, ACTION_RESUME));

        }
        notificationBuilder.addAction(R.drawable.iconnext, "Next", getPendingIntent(this, ACTION_NEXT));
        Picasso.get().load(urlImage)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        notificationBuilder.setLargeIcon(bitmap);
                    }
                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
        Notification notification = notificationBuilder.build();
        startForeground(1, notification);
    }

    //CODE KHI NHAP VAO NOTIFICATION//
    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, BroadcastReceiverAction.class);
        intent.putExtra("action_music", action);
        return  PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //GUI CAC SU KIEN CHOI NHAC DEN BROADCASTRECEIVER//
    private void sendActonToPlayNhacActivity(int action){
        if (mangbaihat != null){
            Intent intent = new Intent("send_data_to_activity");
            intent.putExtra("status_player", isPlaying);
            intent.putExtra("action_music", action);
            intent.putExtra("position_music", positionPlayer);
            intent.putExtra("duration_music", duration);
            intent.putExtra("seektomusic", curentime);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    //HIEN THI THOI GIAN HIEN TAI CUA BAI HAT//
    private void sendTimeCurrent(){
        if (mediaPlayer != null){
            curentime = mediaPlayer.getCurrentPosition();
            sendActonToPlayNhacActivity(ACTION_DURATION);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        sendTimeCurrent();
                    }
                }
            }, 1000);
        }
    }

    //HUY MANG BAIHAT DANG CHOI
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    //THUC THI CHOI NHAC//
    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    class playMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mangbaihat != null && mangbaihat.size() > 0){
                            nextMusic(mangbaihat.size());
                        }
                        CompleteAndStart();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            duration = mediaPlayer.getDuration();
        }
    }
}
