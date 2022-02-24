package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivityB extends AppCompatActivity {
    TextView tvTitle,tvSumTime,tvCurrentTime;
    ImageView imgReplay,imgPlay,imgPrevious,imgNext;
    MediaPlayer mediaPlayer;
    SeekBar sBar;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);
        Log.d("OnCreate MainActivityB","OnCreate MainActivityB");
        init();
        tvTitle.setText("Tet Nay Con Se Ve");
        mediaPlayer=MediaPlayer.create(MainActivityB.this,R.raw.tetnayconseve);
        String time=sharedPreferences.getString("currentTimeB","00:00");
        mediaPlayer.start();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
        tvSumTime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        if(time.equals("00:00")) {
            mediaPlayer.seekTo(0);
        }else{
            if(time.equals(tvSumTime.getText().toString())){
                mediaPlayer.seekTo(0);
            }else{
                String []a=time.split(":");
                int phut=Integer.parseInt(a[0]);
                int giay=Integer.parseInt(a[1]);
                mediaPlayer.seekTo(phut*60*1000+giay*1000);
                sBar.setProgress(phut*60*1000+giay*1000);
            }
        }
        sBar.setMax(mediaPlayer.getDuration());
        displayCurrentTime();
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivityA.class);
                startActivity(intent);
                onPause();
            }
        });
        imgReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                onStart();
            }
        });
    }
    public void init(){
        tvTitle=findViewById(R.id.tvNameMusic);
        imgNext=findViewById(R.id.imgButNext);
        imgPlay=findViewById(R.id.imgButPlay);
        imgReplay=findViewById(R.id.imgButReplay);
        imgPrevious=findViewById(R.id.imgButPrevious);
        tvCurrentTime=findViewById(R.id.tvCurrentTime);
        tvSumTime=findViewById(R.id.tvSumTime);
        sBar=findViewById(R.id.sBarTime);
        sharedPreferences= getSharedPreferences("saveB",MODE_PRIVATE);
    }
    public void displayCurrentTime(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
                tvCurrentTime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                sBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this,400);
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy MainActivityB","OnDestroy MainActivityB");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("OnRestart MainActivityB","OnRestart MainActivityB");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnStart MainActivityB","OnStart MainActivityB");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        Log.d("OnStop MainActivityB","OnStop MainActivityB");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("currentTimeB",tvCurrentTime.getText().toString());
        editor.commit();
        Log.d("OnPause MainActivityB","OnPause MainActivityB");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume MainActivityB","OnResume MainActivityB");
    }
}