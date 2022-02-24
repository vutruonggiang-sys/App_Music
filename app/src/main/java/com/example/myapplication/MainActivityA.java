package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivityA extends Activity {
    TextView tvTitle,tvSumTime,tvCurrentTime;
    ImageView imgReplay,imgPlay,imgPrevious,imgNext;
    MediaPlayer mediaPlayer;
    SeekBar sBar;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.d("OnCreate MainActivityA","OnCreate MainActivityA");

        init();
        tvTitle.setText("De Vuong");
        mediaPlayer=MediaPlayer.create(MainActivityA.this,R.raw.devuong);
        String time=sharedPreferences.getString("currentTime","00:00");
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

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),MainActivityB.class);
                startActivity(intent);
                onPause();
            }
        });
        imgReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
    public void init(){
        tvTitle=findViewById(R.id.tvNameMusic);
        imgNext=findViewById(R.id.imgButNext);
        imgPlay=findViewById(R.id.imgButPlay);
        imgReplay=findViewById(R.id.imgButReplay);
        imgPrevious=findViewById(R.id.imgButPrevious);
        tvCurrentTime=findViewById(R.id.tvCurrentTime);
        tvSumTime=findViewById(R.id.tvSumTime);
        sBar=findViewById(R.id.sBarTime);
        sharedPreferences= getSharedPreferences("save",MODE_PRIVATE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OnDestroy MainActivityA","OnDestroy MainActivityA");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("OnRestart MainActivityA","OnRestart MainActivityA");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnStart MainActivityA","OnStart MainActivityA");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        Log.d("OnStop MainActivityA","OnStop MainActivityA");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("currentTime",tvCurrentTime.getText().toString());
        editor.commit();
        Log.d("OnPause MainActivityA","OnPause MainActivityA");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume MainActivityA","OnResume MainActivityA");
    }
}