package tual.gokmen.pomodorosayaci;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import tual.gokmen.pomodorosayaci.databinding.ActivitySettingsBinding;


public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private SharedPreferences sharedPreferences;
    private boolean keepscreenon,switchstate,switchstate2;
    private int color,colortheme,nowColor;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    // SharedPreferences
        sharedPreferences = getSharedPreferences("screen",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        keepscreenon = sharedPreferences.getBoolean("keep_screen_on",false);
        switchstate = sharedPreferences.getBoolean("switch_state",false);
        switchstate2 = sharedPreferences.getBoolean("switch_state2",false);
        colortheme = sharedPreferences.getInt("themecolor",0);

        // Yeni Tema
        nowColor = ContextCompat.getColor(getApplicationContext(),R.color.bgcolor);
        if (colortheme ==   0){
            binding.mainlayout.setBackgroundColor(nowColor);
            binding.tvEkran.setTextColor(nowColor);
            binding.tvRenkTema.setTextColor(nowColor);
            binding.tvTitresim.setTextColor(nowColor);
            binding.ekranSwitch.setTextColor(nowColor);
            binding.titresimSwitch.setTextColor(nowColor);
            binding.btnKaydet.setTextColor(nowColor);

        }
        else{
            binding.mainlayout.setBackgroundColor(colortheme);
            binding.tvEkran.setTextColor(colortheme);
            binding.tvRenkTema.setTextColor(colortheme);
            binding.tvTitresim.setTextColor(colortheme);
            binding.ekranSwitch.setTextColor(colortheme);
            binding.titresimSwitch.setTextColor(colortheme);
            binding.btnKaydet.setTextColor(colortheme);


        }
        // Secili Switch
        binding.ekranSwitch.setChecked(switchstate);
        binding.titresimSwitch.setChecked(switchstate2);
        // Switch Kontrolu
        if (switchstate2){
            binding.titresimSwitch.setText("Açık");
        }
        else{
            binding.titresimSwitch.setText("Kapalı");

        }

        if (switchstate){
            binding.ekranSwitch.setText("Açık");
        }
        else{
            binding.ekranSwitch.setText("Kapalı");

        }
        // Ekran Açık Kalma Kontrolu
        if (keepscreenon){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        binding.titresimSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.titresimSwitch.setText("Açık");
                    editor.putBoolean("vibrate",true);
                    editor.putBoolean("switch_state2",true);
                    editor.apply();


                }
                else{
                    binding.titresimSwitch.setText("Kapalı");
                    editor.putBoolean("vibrate",false);
                    editor.putBoolean("switch_state2",false);
                    editor.apply();

                }
            }
        });
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
                    editor.putBoolean("keep_screen_on",false);
                    editor.putBoolean("switch_state",b);
                    editor.apply();

                }
            }
        });
        binding.btnKaydet.setOnClickListener(view ->{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        });


        binding.colorred.setOnClickListener(view ->{
            color = getResources().getColor(R.color.bgcolor);

            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();

        });
        binding.colorbrown.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorBrown);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });


        binding.colordeeppurple.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorDeepPurple);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });
        binding.colorgreen.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorGreen);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });
        binding.colorgrey.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorGrey);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });
        binding.colorindigo.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorIndigo);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });

        binding.colorpink.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorPink);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });
        binding.colororange.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorOrange);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });

        binding.colorpurple.setOnClickListener(view -> {
            color = getColor(R.color.bgcolorPurple);
            binding.mainlayout.setBackgroundColor(color);
            editor.putInt("themecolor", color);
            editor.apply();
            recreate();


        });

    }
}