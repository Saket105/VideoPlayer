package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    TextView videoNameTV, videoTimeTV;
    ImageButton forwardBtn, playBtn, backBtn;
    SeekBar seekBar;
    VideoView videoView;
    private RelativeLayout controlsRL, videoRL;
    boolean isOpen = true;
    String videoName, videoPath;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoNameTV = findViewById(R.id.TVVideoTitle);
        videoTimeTV = findViewById(R.id.TVTime);
        forwardBtn = findViewById(R.id.forward);
        backBtn = findViewById(R.id.back);
        playBtn = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);
        videoView = findViewById(R.id.VideoView);
        controlsRL = findViewById(R.id.RLControls);
        videoRL = findViewById(R.id.RLVideo);

        videoName = getIntent().getStringExtra("videoName");
        videoPath = getIntent().getStringExtra("videoPath");

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(videoView.getDuration());
                videoView.start();
            }
        });

        videoNameTV.setText(videoName);

        forwardBtn.setOnClickListener(v -> {
            videoView.seekTo(videoView.getDuration() + 10000);
        });

        backBtn.setOnClickListener(v -> {
            videoView.seekTo(videoView.getDuration() - 10000);
        });

        playBtn.setOnClickListener(v -> {
            if (videoView.isPlaying()){
                videoView.pause();
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
            }else{
                videoView.start();
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_circle_outline_24));
            }
        });

        videoRL.setOnClickListener(v -> {
            if (isOpen){
                hideControls();
                isOpen = false;
            }else {
                showControls();
                isOpen=true;
            }
        });

        setHandler();
        initializedSeekBar();

    }

    private void initializedSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getId()==R.id.seekBar){
                    if (fromUser){
                        videoView.seekTo(progress);
                        videoView.start();
                        int curPos = videoView.getCurrentPosition();
                        videoTimeTV.setText(""+convertTime(videoView.getDuration()-curPos));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    private void setHandler(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (videoView.getDuration()>0){
                    int curPos = videoView.getCurrentPosition();
                    seekBar.setProgress(curPos);
                    videoTimeTV.setText(""+convertTime(videoView.getDuration()-curPos));
                }
                handler.postDelayed(this,0);
            }
        };
        handler.postDelayed(runnable,500);
    }

    private String convertTime(int ms){
        String time;
        int x,seconds, minutes,hours;
        x = ms/1000;
        seconds = x%60;
        x /=60;
        minutes = x%60;
        x/=60;
        hours = x%24;
        if(hours!=0){
            time = String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02D",seconds);
        }else{
            time = String.format("%02D",minutes)+":"+String.format("%02d",seconds);
        }
        return time;
    }

    private void showControls() {
        controlsRL.setVisibility(View.VISIBLE);

        final Window window = this.getWindow();
        if (window==null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null){
            int uioption = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT>=14){
                uioption&= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT>=16){
                uioption&= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT>=19){
                uioption&= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uioption);
        }

    }

    private void hideControls() {

        controlsRL.setVisibility(View.GONE);

        final Window window = this.getWindow();
        if (window==null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        View decorView = window.getDecorView();
        if (decorView != null){
            int uioption = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT>=14){
                uioption |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT>=16){
                uioption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT>=19){
                uioption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uioption);
        }

    }
}