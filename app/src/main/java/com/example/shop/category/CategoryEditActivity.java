package com.example.shop.category;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shop.BaseActivity;
import com.example.shop.R;
import com.google.android.material.textfield.TextInputLayout;

public class CategoryEditActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;
    private final String TAG = "CategoryEditActivity";

    private ImageView ivSelectImage;

    private String filePath;

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        // Initialize views
        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
        ivSelectImage = findViewById(R.id.ivSelectedImage);

        // Load existing category details into views for editing
        loadCategoryDetails();

        // Load default image or existing category image
        String url = "https://cdn.pixabay.com/photo/2016/01/03/00/43/upload-1118929_1280.png";
        Glide
                .with(this)
                .load(url)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);
    }

    private void loadCategoryDetails() {
        String existingCategoryName = tlCategoryName.toString();
        String existingCategoryDescription = tlCategoryDescription.toString();
        String existingCategoryImageUrl = "https://example.com/category/image.jpg";

        // Populate category name
        tlCategoryName.getEditText().setText(existingCategoryName);

        // Populate category description
        tlCategoryDescription.getEditText().setText(existingCategoryDescription);

        // Load existing category image
        Glide
                .with(this)
                .load(existingCategoryImageUrl)
                .apply(new RequestOptions().override(300))
                .into(ivSelectImage);
    }

    public void onClickUpdateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();
            // Logic to update category details
        } catch (Exception ex) {
            Log.e(TAG, "Problem updating category: " + ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            Uri uri = data.getData();

            Glide
                    .with(this)
                    .load(uri)
                    .apply(new RequestOptions().override(300))
                    .into(ivSelectImage);

            // If you want to get the file path from the URI, you can use the following code:
            filePath = getPathFromURI(uri);
        }
    }

    private String getPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
}
