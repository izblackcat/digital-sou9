package com.zarkaoui.digitalsou9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zarkaoui.digitalsou9.classes.Product;
import com.zarkaoui.digitalsou9.classes.ProductRecyclerViewAdapter;
import com.zarkaoui.digitalsou9.classes.User;
import com.zarkaoui.digitalsou9.databinding.ActivityClothesBinding;

import java.util.ArrayList;
import java.util.List;

public class ClothesActivity extends DrawerBaseActivity {
    ActivityClothesBinding activityClothesBinding;

    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter productAdapter;

    private DatabaseReference databaseProductReference;

    private List<Product> productList;


    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityClothesBinding = ActivityClothesBinding.inflate(getLayoutInflater());
        setContentView(activityClothesBinding.getRoot());
        activityTitle("Clothes Category");

        recyclerView = findViewById(R.id.clothesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();


        databaseProductReference = FirebaseDatabase.getInstance().getReference("products");

        databaseProductReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    userId = product.getUserId();
                    productList.add(product);
                }

                productAdapter = new ProductRecyclerViewAdapter(ClothesActivity.this, productList);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClothesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}