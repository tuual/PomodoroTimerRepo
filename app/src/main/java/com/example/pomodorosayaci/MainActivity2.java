package com.example.pomodorosayaci;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodorosayaci.databinding.ActivityMain2Binding;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private CountDownTimer mCountDownTimer;
    private static long START_TIME_IN_MILLIS = 300000;
    private boolean mTimerRunning;
    private long mTimerLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;

    private ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tvTimeText.setText("05:00");
        binding.ImgSettings.setOnClickListener(view -> {
            questionPage();
        });

        binding.btnStart.setOnClickListener(view -> {
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void resetTimer() {
        mTimerLeftInMillis = START_TIME_IN_MILLIS;
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
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();

        mTimerRunning = true;
        updateButtons();


    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        binding.btnStart.setText("Başlat");
        binding.btnReset.setVisibility(View.VISIBLE);
        binding.btnStopOver.setVisibility(View.VISIBLE);


    }

    private void updateCountDownText() {
        int minutes = (int) (mTimerLeftInMillis / 1000) /60;
        int seconds = (int) (mTimerLeftInMillis / 1000) %60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.tvTimeText.setText(timeLeftFormatted);
    }


    private void questionPage() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alertlayout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        Button button2 = view.findViewById(R.id.buttonalert);
        button2.setOnClickListener(view2 ->{
            dialog.cancel();
        });
        dialog.show();



    }
    private void updateButtons(){
        if (mTimerRunning){
            binding.btnReset.setVisibility(View.INVISIBLE);
            binding.btnStart.setText("Durdur");
            binding.btnStopOver.setVisibility(View.INVISIBLE);

        }
        else
        {
            binding.btnStart.setText("Başlat");
            if (mTimerLeftInMillis <1000 ){
                binding.btnStart.setVisibility(View.INVISIBLE);

            }
            else{
                binding.btnStart.setVisibility(View.VISIBLE);

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