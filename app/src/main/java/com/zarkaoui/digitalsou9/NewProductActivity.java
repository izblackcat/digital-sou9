package com.zarkaoui.digitalsou9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zarkaoui.digitalsou9.classes.Product;
import com.zarkaoui.digitalsou9.databinding.ActivityNewProductBinding;
import com.zarkaoui.digitalsou9.enums.ProductCategory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewProductActivity extends DrawerBaseActivity implements View.OnClickListener{
    ActivityNewProductBinding activityNewProductBinding;

    Spinner categorySpinner;
    SpinnerAdapter categorySpinnerAdapter;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView productImg;
    private Button chooseImgBtn;
    private EditText editTextProductName;
    private EditText editTextProductDescription;
    private EditText editTextProductPrice;
    private EditText editTextProductDate;
    private EditText editTextProductLocation;
    private EditText editTextProductQuantity;
    private ProgressBar progressBar;
    private Button addProductBtn;

    private Uri productImgUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String userId;
    private StorageTask uploadProductTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityNewProductBinding = ActivityNewProductBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(activityNewProductBinding.getRoot());
        activityTitle("New product");

        categorySpinner = findViewById(R.id.categorySpinner);
        categorySpinnerAdapter = new ArrayAdapter<ProductCategory>(this, android.R.layout.simple_list_item_1, ProductCategory.values());
        categorySpinner.setAdapter(categorySpinnerAdapter);

        productImg = findViewById(R.id.productImg);
        chooseImgBtn = findViewById(R.id.chooseProductImg);
        editTextProductName = findViewById(R.id.edit_productName);
        editTextProductDescription = findViewById(R.id.edit_productDescription);
        editTextProductPrice = findViewById(R.id.edit_productPrice);
        editTextProductDate = findViewById(R.id.edit_productDate);

        editTextProductQuantity = findViewById(R.id.edit_productQuantity);
        editTextProductLocation = findViewById(R.id.edit_productLocation);
        addProductBtn = findViewById(R.id.addProductBtn);
        progressBar = findViewById(R.id.progressBar);

        //fix the date :
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String date = simpleDateFormat.format(new Date());
        editTextProductDate.setText(date);


        storageReference = FirebaseStorage.getInstance().getReference("products");
        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


        chooseImgBtn.setOnClickListener(this);
        addProductBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chooseProductImg:
                chooseProductImage();
                break;
            case R.id.addProductBtn:
                if (uploadProductTask != null && uploadProductTask.isInProgress()) {
                    Toast.makeText(NewProductActivity.this, "Uploading already in progress", Toast.LENGTH_LONG).show();
                } else{
                    uploadProduct();
                }
                break;
        }
    }


    private void chooseProductImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            productImgUri = data.getData();
            Picasso.get().load(productImgUri).into(productImg);
        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadProduct() {
        String productName = editTextProductName.getText().toString().trim();
        String productDescription = editTextProductDescription.getText().toString().trim();
        String productPrice = editTextProductPrice.getText().toString().trim();
        String productDate = editTextProductDate.getText().toString().trim();
        String productLocation = editTextProductLocation.getText().toString().trim();
        String productCategory = categorySpinner.getSelectedItem().toString().trim();
        String productQuantity = editTextProductQuantity.getText().toString().trim();

        if(productName.isEmpty()){
            editTextProductName.setError("Product name is required!");
            editTextProductName.requestFocus();
            return;
        }
        if(productDescription.isEmpty()){
            editTextProductDescription.setError("Product description is required!");
            editTextProductDescription.requestFocus();
            return;
        }

        if(productPrice.isEmpty()){
            editTextProductPrice.setError("Product price is required!");
            editTextProductPrice.requestFocus();
            return;
        }

        if(productLocation.isEmpty()){
            editTextProductLocation.setError("Product location is required!");
            editTextProductLocation.requestFocus();
            return;
        }


        if(productQuantity.isEmpty()){
            editTextProductQuantity.setError("Product quantity is required!");
            editTextProductQuantity.requestFocus();
            return;
        }

        if(productImgUri != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(productImgUri));
            uploadProductTask = fileReference.putFile(productImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(NewProductActivity.this, "The product was successfully added", Toast.LENGTH_LONG).show();
                    /*Product product = new Product(productName, productDescription,productDate, productLocation, productPrice, productCategory, productQuantity, taskSnapshot.getUploadSessionUri().toString(), userId);
                    String productId = databaseReference.push().getKey();
                    databaseReference.child(productId).setValue(product);*/

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
                    Product product = new Product(productName, productDescription,productDate, productLocation, productPrice, productCategory, productQuantity, taskSnapshot.getUploadSessionUri().toString(), userId);

                    String productId = databaseReference.push().getKey();
                    databaseReference.child(productId).setValue(product);

                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(NewProductActivity.this, MainActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show();
        }
    }
}