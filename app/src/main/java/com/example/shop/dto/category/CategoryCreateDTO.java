package com.example.shop.dto.category;

import android.graphics.Bitmap;

public class CategoryCreateDTO {
    private String name;
    private String description;
    private Bitmap imageData;

    public Bitmap getImageData() {
        return imageData;
    }

    public void setImageData(Bitmap imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}