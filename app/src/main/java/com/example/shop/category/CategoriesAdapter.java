package com.example.shop.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.constants.Urls;
import com.example.shop.dto.category.CategoryItemDTO;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private List<CategoryItemDTO> items;

    public CategoriesAdapter(List<CategoryItemDTO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view, parent, false);
        return new CategoryCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(items!=null && position<items.size()) {
            CategoryItemDTO item = items.get(position);
            holder.getCategoryName().setText(item.getName());
            holder.getCategoryDescription().setText(item.getDescription());

            String url = Urls.BASE+"/images/"+item.getImage();
            if(item.getImage().isEmpty())
                url = "https://fundatia.moldcell.md/wp-content/themes/consultix/images/no-image-found-360x250.png";
            Glide
                    .with(holder.itemView.getContext())
                    .load(url)
                    .into(holder.getIvCategoryImage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
