package com.modefin.app.view;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.modefin.app.R;
import com.modefin.app.model.ProductColor;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MakepListViewHolder> {

    private List<ProductColor> productColorList;

    public ProductAdapter(ArrayList<ProductColor> productColorList) {
        this.productColorList = productColorList;
    }

    @NonNull
    @Override
    public ProductAdapter.MakepListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);

        return new ProductAdapter.MakepListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MakepListViewHolder holder, int position) {
        ProductColor productColor = productColorList.get(position);

        holder.tvColor.setBackgroundColor(Color.parseColor(productColor.getHex_value()));
        if(!TextUtils.isEmpty(productColor.getColour_name())) {
            holder.tvName.setText(productColor.getColour_name());
        }

    }

    @Override
    public int getItemCount() {
        return productColorList.size();
    }

    public static class MakepListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvColor, tvName;

        public MakepListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvName = itemView.findViewById(R.id.tvName);

        }
    }

}
