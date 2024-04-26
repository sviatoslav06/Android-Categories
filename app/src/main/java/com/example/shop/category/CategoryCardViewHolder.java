package com.example.shop.category;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

public class CategoryCardViewHolder extends RecyclerView.ViewHolder {
    private TextView categoryName;
    private TextView categoryDescription;

    public ImageView getIvCategoryImage() {
        return ivCategoryImage;
    }

    public void setIvCategoryImage(ImageView ivCategoryImage) {
        this.ivCategoryImage = ivCategoryImage;
    }

    private ImageView ivCategoryImage;
    public CategoryCardViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryName = itemView.findViewById(R.id.categoryName);
        categoryDescription = itemView.findViewById(R.id.categoryDescription);
        ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public TextView getCategoryDescription() {
        return categoryDescription;
    }
}
