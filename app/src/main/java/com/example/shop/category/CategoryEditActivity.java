package com.example.shop.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.shop.BaseActivity;
import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends BaseActivity {

    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    ImageView ivSelectedImage;
    CategoryItemDTO categoryItem;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);

        categoryId = getIntent().getIntExtra("categoryId", -1);

        loadCategoryData();
    }

    private void loadCategoryData() {
        ApplicationNetwork.getInstance().getCategoriesApi().getCategoryById(categoryId)
                .enqueue(new Callback<CategoryItemDTO>() {
                    @Override
                    public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                        if (response.isSuccessful()) {
                            categoryItem = response.body();
                            if (categoryItem != null) {
                                tlCategoryName.getEditText().setText(categoryItem.getName());
                                tlCategoryDescription.getEditText().setText(categoryItem.getDescription());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryItemDTO> call, Throwable t) {
                        Log.e("--CategoryEditActivity--", "Failed to load category data: " + t.getMessage());
                    }
                });
    }

    public void onClickUpdateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();

            categoryItem.setName(name);
            categoryItem.setDescription(description);

            ApplicationNetwork.getInstance().getCategoriesApi().update(categoryId, categoryItem)
                    .enqueue(new Callback<CategoryItemDTO>() {
                        @Override
                        public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(CategoryEditActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryItemDTO> call, Throwable t) {
                            Log.e("--CategoryEditActivity--", "Failed to update category: " + t.getMessage());
                        }
                    });
        } catch (Exception ex) {
            Log.e("--CategoryEditActivity--", "Problem updating category: " + ex.getMessage());
        }
    }
}
