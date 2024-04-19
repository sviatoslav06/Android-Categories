package com.example.shop.network;

import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @POST("/api/categories")
    public Call<CategoryItemDTO> create(@Body CategoryCreateDTO model);

    @PUT("/api/categories/{id}")
    Call<CategoryItemDTO> update(@Path("id") int id, @Body CategoryItemDTO model);

    @GET("/api/categories/{id}")
    Call<CategoryItemDTO> getCategoryById(@Path("id") int id);

    @DELETE("/api/categories/{id}")
    Call<Void> deleteCategory(@Path("id") int categoryId);
}
