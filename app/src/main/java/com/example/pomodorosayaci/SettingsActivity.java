package com.example.pomodorosayaci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pomodorosayaci.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private SharedPreferences sharedPreferences;
    private boolean keepscreenon,switchstate;
    private int color;
    private int colortheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("screen",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        keepscreenon = sharedPreferences.getBoolean("keep_screen_on",false);
        switchstate = sharedPreferences.getBoolean("switch_state",false);
        colortheme = sharedPreferences.getInt("themecolor",0);
        binding.mainlayout.setBackgroundColor(colortheme);

        if (switchstate){
            binding.ekranSwitch.setText("Açık");
        }
        else{
            binding.ekranSwitch.setText("Kapalı");

        }

        binding.ekranSwitch.setChecked(switchstate);
        if (keepscreenon){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        binding.ekranSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.ekranSwitch.setText("Açık");
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                    editor.putBoolean("keep_screen_on",true);
                    editor.putBoolean("switch_state",b);
                    editor.apply();
                }
                else{
                    binding.ekranSwitch.setText("Kapalı");

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("keep_screen_on",true);
                    editor.putBoolean("switch_state",b);
                    editor.apply();

                }
            }
        });
        binding.completeColor.setOnClickListener(view ->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.colorblue.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorBlue);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colorred.setOnClickListener(view ->{
            color = getColor(R.color.bgcolor);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();

        });
        binding.colorbrown.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorBrown);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colordeeporange.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorDeepOrange);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });

        binding.colordeeppurple.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorDeepPurple);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colorgreen.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorGreen);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colorgrey.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorGrey);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colorindigo.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorIndigo);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });

        binding.colorpink.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorPink);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.colororange.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorOrange);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });

        binding.colorpurple.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorPurple);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
        binding.coloryellow.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorYellow);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();


        });
    }
}