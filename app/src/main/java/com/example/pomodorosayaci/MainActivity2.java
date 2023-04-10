package com.example.pomodorosayaci;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pomodorosayaci.databinding.ActivityMain2Binding;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private CountDownTimer mCountDownTimer;
    private static long START_TIME_IN_MILLIS =  5 * 60 * 1000;
    private boolean mTimerRunning,vibrate,isScreenOn;
    private long mTimerLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;
    private int mProgressBar,nowColor;

    private ImageView img;
    private ActivityMain2Binding binding;
    private MediaPlayer mediaPlayer;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvTimeText.setText("05:00");
        mediaPlayer = null;
        img = findViewById(R.id.imgStart);

        SharedPreferences sharedPreferences = getSharedPreferences("screen", Context.MODE_PRIVATE);
        isScreenOn = sharedPreferences.getBoolean("keep_screen_on", true);
        int themeColor = sharedPreferences.getInt("themecolor",0);
        vibrate = sharedPreferences.getBoolean("vibrate", false);
        binding.mainlayout.setBackgroundColor(themeColor);
        binding.btnStopOver.setTextColor(themeColor);
        nowColor = ContextCompat.getColor(getApplicationContext(),R.color.bgcolor);


        if (themeColor ==   0){
            binding.mainlayout.setBackgroundColor(nowColor);
            binding.btnStopOver.setTextColor(nowColor);
        }
        else{

            binding.mainlayout.setBackgroundColor(themeColor);
            binding.btnStopOver.setTextColor(themeColor);

        }
        if (isScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }



        binding.ImgSettings.setOnClickListener(view -> {
            settingspage();
        });

        binding.imgStart.setOnClickListener(view -> {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();

            }
        });

        binding.btnReset.setOnClickListener(view -> {
            resetTimer();
        });

        binding.btnStopOver.setOnClickListener(view -> {
            overPage();
        });


        updateCountDownText();
    }


    private void overPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void resetTimer() {
        mTimerLeftInMillis = START_TIME_IN_MILLIS;
        binding.progressBar.setProgress(0);
        stopAlarm();
        updateCountDownText();
        updateButtons();


    }


    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimerLeftInMillis;
        binding.progressBar.setMax(5 *60); // 5 dakika için
        mCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimerLeftInMillis = l;
                updateCountDownText();
                binding.tvTimeText.setVisibility(View.VISIBLE);
                int progress = (int) ((5 * 60 * 1000 - mTimerLeftInMillis) / 1000);
                binding.progressBar.setProgress(progress);

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();

                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                }
                mediaPlayer.setOnCompletionListener(mediaPlayer1 -> {
                    stopAlarm();

                });
                mediaPlayer.start();

                if (vibrate){
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        vibrator.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else{
                        vibrator.vibrate(2000);
                    }

                }
                else{
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    if (vibrator.hasVibrator()){
                        vibrator.cancel();
                    }
                }


            }
        }.start();

        mTimerRunning = true;
        updateButtons();


    }
    @Override
    protected void onStop() {
        super.onStop();
        stopAlarm();
    }

    private void stopAlarm() {
        mediaPlayer=null;
    }


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        binding.tvTimeText.setVisibility(View.VISIBLE);
        img.setImageResource(R.drawable.baseline_play_circle_24);

        binding.btnReset.setVisibility(View.VISIBLE);
        binding.btnStopOver.setVisibility(View.INVISIBLE);


    }



    private void updateCountDownText() {
        int minutes = (int) (mTimerLeftInMillis / 1000) /60;
        int seconds = (int) (mTimerLeftInMillis / 1000) %60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.tvTimeText.setText(timeLeftFormatted);
    }


    private void settingspage() {

        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
        finish();


    }
    private void updateButtons(){
        if (mTimerRunning){
            binding.btnReset.setVisibility(View.INVISIBLE);
            img.setImageResource(R.drawable.baseline_pause_24);
            binding.btnStopOver.setVisibility(View.INVISIBLE);
            binding.ImgSettings.setVisibility(View.INVISIBLE);
            binding.btnStopOver.setVisibility(View.INVISIBLE);




        }
        else
        {
            img.setImageResource(R.drawable.baseline_play_circle_24);
            if (mTimerLeftInMillis <1000 ){
                binding.imgStart.setVisibility(View.INVISIBLE);
                binding.ImgSettings.setVisibility(View.INVISIBLE);
                binding.btnStopOver.setVisibility(View.INVISIBLE);


            }
            else{
                binding.imgStart.setVisibility(View.VISIBLE);
                binding.ImgSettings.setVisibility(View.VISIBLE);
                binding.btnStopOver.setVisibility(View.VISIBLE);


            }
            if(mTimerLeftInMillis < START_TIME_IN_MILLIS){
                binding.btnReset.setVisibility(View.VISIBLE);
                binding.ImgSettings.setVisibility(View.VISIBLE);
                binding.btnStopOver.setVisibility(View.VISIBLE);




            }
            else{

            }
        }

    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft",mTimerLeftInMillis);
        outState.putBoolean("timerRunning",mTimerRunning);
        outState.putLong("endTime",mEndTime);
        outState.putInt("progresTime",mProgressBar);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimerLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        mProgressBar = savedInstanceState.getInt("progresTime");
        assert binding.progressBar != null;
        binding.progressBar.setProgress((int) mProgressBar);

        updateCountDownText();
        updateButtons();

        if (mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimerLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
}