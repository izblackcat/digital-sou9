package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityFurnitureBinding;

public class FurnitureActivity extends DrawerBaseActivity {
    ActivityFurnitureBinding activityFurnitureBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityFurnitureBinding = ActivityFurnitureBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityFurnitureBinding.getRoot());
        activityTitle("Furniture Category");
    }
}