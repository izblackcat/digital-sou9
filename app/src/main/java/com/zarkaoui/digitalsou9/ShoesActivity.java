package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityShoesBinding;

public class ShoesActivity extends DrawerBaseActivity {
    ActivityShoesBinding activityShoesBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityShoesBinding = ActivityShoesBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityShoesBinding.getRoot());
        activityTitle("Shoes Activity");
    }
}