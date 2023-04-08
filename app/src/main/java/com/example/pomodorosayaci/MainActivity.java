package com.example.pomodorosayaci;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodorosayaci.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String buttonText;
    private CountDownTimer mCountDownTimer;
    private static long START_TIME_IN_MILLIS = 1500000;
    private boolean mTimerRunning;
    private long mTimerLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;
    private int i = 0;
    private ImageView img;
    private Drawable timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("screen", Context.MODE_PRIVATE);
        boolean isScreenOn = sharedPreferences.getBoolean("keep_screen_on", false);
        int themeColor = sharedPreferences.getInt("themecolor",0);
        binding.mainlayout.setBackgroundColor(themeColor);
        binding.btnStopOver.setTextColor(themeColor);



        if (isScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        binding.progressBar.setProgress((int)i);
        binding.tvTimeText.setText("25:00");
        img = findViewById(R.id.imgStart);
        binding.ImgSettings.setOnClickListener(view -> {
            settingsPage();
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
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);

    }


    private void resetTimer() {
        mTimerLeftInMillis = START_TIME_IN_MILLIS;
        binding.progressBar.setProgress(0);
        i = 0;
        updateCountDownText();
        updateButtons();


    }


    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimerLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimerLeftInMillis = l;
                updateCountDownText();
                if (i<=250000){
                    i++;
                    binding.progressBar.setProgress((int) ((int) i*100/(150000/1000)));
                }

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
                i++;

            }
        }.start();

        mTimerRunning = true;
        updateButtons();


    }


    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        binding.tvTimeText.setVisibility(View.VISIBLE);
        img.setImageResource(R.drawable.baseline_play_circle_24);
        binding.btnReset.setVisibility(View.VISIBLE);
        binding.btnStopOver.setVisibility(View.VISIBLE);


    }

    private void updateCountDownText() {
        int minutes = (int) (mTimerLeftInMillis / 1000) /60;
        int seconds = (int) (mTimerLeftInMillis / 1000) %60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.tvTimeText.setText(timeLeftFormatted);
    }


    private void settingsPage() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
        finish();


    }

    private void updateButtons(){
        if (mTimerRunning){
            binding.btnReset.setVisibility(View.INVISIBLE);
            img.setImageResource(R.drawable.baseline_pause_24);
            binding.btnStopOver.setVisibility(View.INVISIBLE);

        }
        else
        {
            img.setImageResource(R.drawable.baseline_play_circle_24);

            if (mTimerLeftInMillis <1000 ){
                binding.imgStart.setVisibility(View.INVISIBLE);

            }
            else{
                binding.imgStart.setVisibility(View.VISIBLE);

            }
            if(mTimerLeftInMillis < START_TIME_IN_MILLIS){
                binding.btnReset.setVisibility(View.VISIBLE);

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
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimerLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");
        updateCountDownText();
        updateButtons();

        if (mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimerLeftInMillis = mEndTime - System.currentTimeMillis();

            startTimer();
        }
    }
}


