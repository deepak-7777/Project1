package com.example.project1.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project1.Adapter.DetailImageAdapter;
import com.example.project1.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public void back(View view) {
       startActivity(new Intent(DetailActivity.this, MainActivity.class));
    }

    TextView titleTxt, descriptionTxt, priceTxt, oldPriceTxt;
    RecyclerView detailRecyclerView;
    private boolean isFavorite = false;
    ImageView favBtn;
   private ImageView shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /// Initialize Views
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        priceTxt = findViewById(R.id.priceTxt);
        oldPriceTxt = findViewById(R.id.oldPriceTxt);
        detailRecyclerView = findViewById(R.id.detailRecyclerView);
        favBtn = findViewById(R.id.favBtn);
        shareBtn = findViewById(R.id.shareBtn);

        /// Get data from intent
        ArrayList<String> imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String price = getIntent().getStringExtra("price");
        String oldPrice = getIntent().getStringExtra("oldPrice");

        /// Set text content
        titleTxt.setText(title);
        descriptionTxt.setText(description);
        priceTxt.setText(price);

        /// Old Price with strike-through and grey color
        oldPriceTxt.setText(oldPrice);
        oldPriceTxt.setPaintFlags(oldPriceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        oldPriceTxt.setTextColor(Color.GRAY);

        /// Set up RecyclerView
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DetailImageAdapter adapter = new DetailImageAdapter(this, imageUrls);
        detailRecyclerView.setAdapter(adapter);

                ///  share box open karne ka code
        String title1 = getIntent().getStringExtra("title");

        shareBtn.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome product: " + title1);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });


        ///   favourite button ke liye
        favBtn.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            if (isFavorite) {
                favBtn.setImageResource(R.drawable.fav_filled);
            } else {
                favBtn.setImageResource(R.drawable.favourite);
            }
            Toast.makeText(this, isFavorite ? "Added to Favourites" : "Removed from Favourites", Toast.LENGTH_SHORT).show();
        });
    }
}
