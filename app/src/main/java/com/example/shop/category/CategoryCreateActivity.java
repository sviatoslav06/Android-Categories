package com.example.shop.category;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.shop.BaseActivity;
import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    ImageView ivSelectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
    }
    public void onClickCreateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();
            Drawable drawable = ivSelectedImage.getDrawable();
            if(drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                CategoryCreateDTO dto = new CategoryCreateDTO();
                dto.setName(name);
                dto.setDescription(description);
                dto.setImageData(bitmap);
                ApplicationNetwork.getInstance()
                        .getCategoriesApi()
                        .create(dto)
                        .enqueue(new Callback<CategoryItemDTO>() {
                            @Override
                            public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                                if(response.isSuccessful())
                                {
                                    Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                            }
                        });
            } else {

            }
        }
        catch(Exception ex) {
            Log.e("--CategoryCreateActivity--", "Problem create "+ ex.getMessage());
        }
    }


}