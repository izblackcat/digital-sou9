package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zarkaoui.digitalsou9.databinding.ActivityBooksBinding;

public class BooksActivity extends DrawerBaseActivity {
    ActivityBooksBinding activityBooksBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBooksBinding = ActivityBooksBinding.inflate(getLayoutInflater());
        setContentView(activityBooksBinding.getRoot());
        activityTitle("Books Category");
    }
}