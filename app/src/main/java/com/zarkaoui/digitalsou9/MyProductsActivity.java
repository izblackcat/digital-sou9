package com.zarkaoui.digitalsou9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zarkaoui.digitalsou9.databinding.ActivityMyProductsBinding;

public class MyProductsActivity extends DrawerBaseActivity implements View.OnClickListener{

    ActivityMyProductsBinding activityMyProductsBinding;
    Button addNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyProductsBinding = ActivityMyProductsBinding.inflate(getLayoutInflater());
        setContentView(activityMyProductsBinding.getRoot());
        activityTitle("My products");

        addNewProduct = findViewById(R.id.addNewProductBtn);
        addNewProduct.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.addNewProductBtn:
                startActivity(new Intent(this, NewProductActivity.class));
                break;
        }
    }
}