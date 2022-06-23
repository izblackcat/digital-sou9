package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityRecommendationsBinding;

public class RecommendationsActivity extends DrawerBaseActivity {

    ActivityRecommendationsBinding activityRecommendationsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecommendationsBinding = ActivityRecommendationsBinding.inflate(getLayoutInflater());
        setContentView(activityRecommendationsBinding.getRoot());
        activityTitle("Recommendations");
    }
}