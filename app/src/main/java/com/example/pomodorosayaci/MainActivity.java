package com.example.pomodorosayaci;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.pomodorosayaci.databinding.ActivityMainBinding;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String buttonText;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long duration = TimeUnit.MINUTES.toMillis(250000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTimeText.setText("25:00");

        binding.ImgSettings.setOnClickListener(view -> {
            settingsPage();
        });

        binding.btnStart.setOnClickListener(view -> {
            startStop();
        });



    }
    private void startStop(){
        if (timerRunning){
            countDownTimerProcess();
        }
        else{
            pauseTimer();
        }

    }

    private void settingsPage() {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);


    }

    private void countDownTimerProcess() {
            countDownTimer = new CountDownTimer(duration,1000) {
                @Override
                public void onTick(long l) {
                    String sDuration = String.format(Locale.ENGLISH,"%02d : %02d",TimeUnit.MILLISECONDS.toMinutes(l),
                    TimeUnit.MILLISECONDS.toSeconds(l)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                    binding.tvTimeText.setText(sDuration);

                }

                @Override
                public void onFinish() {
                    binding.tvTimeText.setText("00:00");
                    Toast.makeText(MainActivity.this, "Süre Tamamlandı", Toast.LENGTH_SHORT).show();
                }
            }.start();
                 binding.btnStart.setText("DURDUR");
                timerRunning = true;



    }
    private void pauseTimer(){
        countDownTimer.cancel();
        timerRunning = false;
        binding.btnStart.setText("BAŞLAT");



    }


}