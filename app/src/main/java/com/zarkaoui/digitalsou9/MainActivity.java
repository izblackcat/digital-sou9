package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zarkaoui.digitalsou9.databinding.ActivityMainBinding;

public class MainActivity extends DrawerBaseActivity implements View.OnClickListener{

    private LinearLayout clothesLayout;
    private LinearLayout foodLayout;
    private LinearLayout booksLayout;
    private LinearLayout technologyLayout;
    private LinearLayout shoesLayout;
    private LinearLayout otherLayout;
    private LinearLayout furnitureLayout;

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityTitle("Categories");

        clothesLayout = findViewById(R.id.clothesCategory);
        booksLayout = findViewById(R.id.booksCategory);
        shoesLayout = findViewById(R.id.shoesCategory);
        technologyLayout = findViewById(R.id.technologyCategory);
        foodLayout = findViewById(R.id.foodCategory);
        furnitureLayout = findViewById(R.id.furnitureCategory);
        otherLayout = findViewById(R.id.otherCategory);

        clothesLayout.setOnClickListener(this);
        booksLayout.setOnClickListener(this);
        shoesLayout.setOnClickListener(this);
        technologyLayout.setOnClickListener(this);
        foodLayout.setOnClickListener(this);
        furnitureLayout.setOnClickListener(this);
        otherLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.clothesCategory:
                startActivity(new Intent(this, ClothesActivity.class));
                break;
            case R.id.booksCategory:
                startActivity(new Intent(this, BooksActivity.class));
                break;
            case R.id.shoesCategory:
                startActivity(new Intent(this, ShoesActivity.class));
                break;
            case R.id.foodCategory:
                startActivity(new Intent(this, FoodActivity.class));
                break;
            case R.id.technologyCategory:
                startActivity(new Intent(this, TechnologyActivity.class));
                break;
            case R.id.furnitureCategory:
                startActivity(new Intent(this, FurnitureActivity.class));
                break;
            case R.id.otherCategory:
                startActivity(new Intent(this, OtherCategoryActivity.class));
                break;
        }
    }
}