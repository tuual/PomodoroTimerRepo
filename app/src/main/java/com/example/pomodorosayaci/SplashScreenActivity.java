package com.example.pomodorosayaci;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodorosayaci.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    private Animation animation;
    private Handler handler;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTheme(R.style.SplashTheme);
        handler = new Handler();
        Thread logoAnim = new Thread(){
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_intro_logo);
                binding.splashlogo.startAnimation(animation);
            }
        };
        logoAnim.start();
        transt();


    }
    private void transt(){
        handler.postDelayed(
                () -> {
                    intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                },
        2000);
    }


}