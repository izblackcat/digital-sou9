package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityMyProductsBinding;

public class MyProductsActivity extends DrawerBaseActivity {

    ActivityMyProductsBinding activityMyProductsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProductsBinding = ActivityMyProductsBinding.inflate(getLayoutInflater());
        setContentView(activityMyProductsBinding.getRoot());
        activityTitle("My products");
    }
}