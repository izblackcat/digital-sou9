package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityFoodBinding;

public class FoodActivity extends DrawerBaseActivity {
    ActivityFoodBinding activityFoodBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityFoodBinding = ActivityFoodBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityFoodBinding.getRoot());
        activityTitle("Food Category");
    }
}