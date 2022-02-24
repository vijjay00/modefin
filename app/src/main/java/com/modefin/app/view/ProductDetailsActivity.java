package com.modefin.app.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.modefin.app.MyApplication;
import com.modefin.app.R;
import com.modefin.app.model.MakeupResponse;
import com.modefin.app.model.ProductColor;

import java.util.ArrayList;
import java.util.Objects;

public class ProductDetailsActivity extends AppCompatActivity {

    public static void startProductDetailsActivity(Context mContext, MakeupResponse makeupResponse) {
        MyApplication.getInstance().setMakeupResponse(makeupResponse);
        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
        mContext.startActivity(intent);
    }

    private MakeupResponse makeupResponse;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private TextView tvTitle, tvBrand, tvDesc;
    private AppCompatImageView productImage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.product_details);

        makeupResponse = MyApplication.getInstance().getMakeupResponse();

        recyclerView = findViewById(R.id.recyclerView);
        tvTitle = findViewById(R.id.tvTitle);
        tvBrand = findViewById(R.id.tvBrand);
        tvDesc = findViewById(R.id.tvDesc);
        productImage = findViewById(R.id.productImage);
        progressBar = findViewById(R.id.progress);

        tvTitle.setText(makeupResponse.getName());
        tvBrand.setText(makeupResponse.getBrand());
        if (!TextUtils.isEmpty(makeupResponse.getDescription())) {
            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText(makeupResponse.getDescription());
        }

        Glide.with(this)
                .load(makeupResponse.getImage_link())
                .error(R.drawable.no_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(productImage);

        if (makeupResponse.getProduct_colors().size() > 0) {
            productAdapter = new ProductAdapter((ArrayList<ProductColor>) makeupResponse.getProduct_colors());
            if (makeupResponse.getProduct_colors().size() > 3) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, makeupResponse.getProduct_colors().size()));
            }
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(productAdapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
