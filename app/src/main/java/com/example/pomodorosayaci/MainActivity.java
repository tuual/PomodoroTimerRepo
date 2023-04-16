package com.example.pomodorosayaci;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.pomodorosayaci.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String buttonText;
    private CountDownTimer mCountDownTimer;
    private static long START_TIME_IN_MILLIS = 25 * 60 * 1000;
    private boolean mTimerRunning,vibrate;
    private long mTimerLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;
    private int i = 0;
    private ImageView img;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaPlayer = null;
        SharedPreferences sharedPreferences = getSharedPreferences("screen", Context.MODE_PRIVATE);
        boolean isScreenOn = sharedPreferences.getBoolean("keep_screen_on", false);
        int themeColor = sharedPreferences.getInt("themecolor",0);
        vibrate = sharedPreferences.getBoolean("vibrate", false);



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
        finish();
    }


    private void resetTimer() {
        mTimerLeftInMillis = START_TIME_IN_MILLIS;
        binding.progressBar.setProgress(0);
        i = 0;
        stopAlarm();
        updateCountDownText();
        updateButtons();


    }


    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimerLeftInMillis;
        binding.progressBar.setMax(25 * 60);
        mCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimerLeftInMillis = l;
                updateCountDownText();



                int progress = (int) ((25 * 60 * 1000 - mTimerLeftInMillis) / 1000);
                binding.progressBar.setProgress(progress);


            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
                binding.progressBar.setProgress(25 * 60);
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
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else{
                        vibrator.vibrate(1000);
                    }

                }
                showNotification();

            }
        }.start();

        mTimerRunning = true;
        updateButtons();


    }
    private void stopAlarm(){
        mediaPlayer = null;
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        binding.tvTimeText.setVisibility(View.VISIBLE);
        img.setImageResource(R.drawable.baseline_play_circle_24);
        binding.btnReset.setVisibility(View.VISIBLE);



    }

    private void updateCountDownText() {
        int minutes = (int) (mTimerLeftInMillis / 1000) /60;
        int seconds = (int) (mTimerLeftInMillis / 1000) %60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        binding.tvTimeText.setText(timeLeftFormatted);
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "my_channel_id";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.baseline_timer_24)
                .setContentTitle("Pomodoro Bitti!")
                .setContentText("25 dakika dolmuştur.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Pomodoro Bitti!";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, builder.build());



    }
    private void settingsPage() {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void updateButtons(){
        if (mTimerRunning){
            binding.btnReset.setVisibility(View.INVISIBLE);
            img.setImageResource(R.drawable.baseline_pause_24);
            binding.btnStopOver.setVisibility(View.INVISIBLE);
            binding.ImgSettings.setVisibility(View.INVISIBLE);


        }
        else
        {
            img.setImageResource(R.drawable.baseline_play_circle_24);

            if (mTimerLeftInMillis <1000 ){
                binding.imgStart.setVisibility(View.INVISIBLE);
                binding.ImgSettings.setVisibility(View.INVISIBLE);


            }
            else{
                binding.imgStart.setVisibility(View.VISIBLE);
                binding.ImgSettings.setVisibility(View.VISIBLE);
                binding.btnStopOver.setVisibility(View.VISIBLE);



            }
            if(mTimerLeftInMillis < START_TIME_IN_MILLIS){
                binding.btnReset.setVisibility(View.VISIBLE);
                binding.ImgSettings.setVisibility(View.VISIBLE);


            }
            else{

            }
        }

    }

}


