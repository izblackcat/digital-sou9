package com.zarkaoui.digitalsou9.classes;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zarkaoui.digitalsou9.ClothesActivity;
import com.zarkaoui.digitalsou9.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;


    public ProductRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product = productList.get(position);

        holder.cardName.setText(product.getProductName());
        holder.cardPrice.setText(product.getPrice());
        holder.cardDescription.setText(product.getDescription());
        holder.cardLocation.setText(product.getLocation());
        Picasso.get().load(product.getImageUrl()).placeholder(R.drawable.new_product_img).fit().centerCrop().into(holder.cardImage);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView cardName;
        public TextView cardPrice;
        public TextView cardDescription;
        public TextView cardLocation;
        public ImageView cardImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.card_productName);
            cardPrice = itemView.findViewById(R.id.card_productPrice);
            cardDescription = itemView.findViewById(R.id.card_productDescription);
            cardLocation = itemView.findViewById(R.id.card_productLocation);
            cardImage = itemView.findViewById(R.id.card_imageView);
        }
    }
}
