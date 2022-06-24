package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityTechnologyBinding;

public class TechnologyActivity extends DrawerBaseActivity {
    ActivityTechnologyBinding activityTechnologyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityTechnologyBinding = ActivityTechnologyBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityTechnologyBinding.getRoot());
        activityTitle("Technology Category");
    }
}