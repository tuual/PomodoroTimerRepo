package com.example.pomodorosayaci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pomodorosayaci.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.pomodorosayaci.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}