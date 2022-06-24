package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityOtherCategoryBinding;

public class OtherCategoryActivity extends DrawerBaseActivity {
    ActivityOtherCategoryBinding activityOtherCategoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityOtherCategoryBinding = ActivityOtherCategoryBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityOtherCategoryBinding.getRoot());
        activityTitle("Other Category");
    }
}