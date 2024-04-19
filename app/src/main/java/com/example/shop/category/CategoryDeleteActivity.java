package com.example.shop.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.services.ApplicationNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDeleteActivity extends AppCompatActivity {

    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_delete);

        categoryId = getIntent().getIntExtra("categoryId", -1);
    }

    public void onDeleteCategory(View view) {
        // Call API to delete the category
        ApplicationNetwork.getInstance().getCategoriesApi().deleteCategory(categoryId)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(CategoryDeleteActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("CategoryDeleteActivity", "Failed to delete category. Response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("CategoryDeleteActivity", "Failed to delete category: " + t.getMessage());
                    }
                });
    }
}
