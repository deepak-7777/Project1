package com.example.project1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project1.Activity.DetailActivity;
import com.example.project1.Model.CategoryModel;
import com.example.project1.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final Context context;
    private final List<CategoryModel> categoryList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel model = categoryList.get(position);

        Glide.with(context)
                .load(model.getImageUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            ArrayList<String> images = new ArrayList<>();
            images.add(model.getImageUrl());

            intent.putStringArrayListExtra("imageUrls", images);
            intent.putExtra("title", model.getTitle());
            intent.putExtra("description", model.getDescription());
            intent.putExtra("price", model.getPrice());
            intent.putExtra("oldPrice", model.getOldPrice());

            context.startActivity(intent);
        });

    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoryImage);
        }
    }
}
