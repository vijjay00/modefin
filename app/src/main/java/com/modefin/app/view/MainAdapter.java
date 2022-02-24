package com.modefin.app.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.modefin.app.R;
import com.modefin.app.model.MakeupResponse;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MakepListViewHolder> {

    private List<MakeupResponse> makeupResponseList;
    private Context context;

    public MainAdapter(Context context, ArrayList<MakeupResponse> makeupResponseList) {
        this.makeupResponseList = makeupResponseList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdapter.MakepListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makeup_list, parent, false);

        return new MainAdapter.MakepListViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MakepListViewHolder holder, int position) {
        MakeupResponse makeupResponse = makeupResponseList.get(position);

        holder.tvTitle.setText(makeupResponse.getName());
        holder.tvBrand.setText(makeupResponse.getBrand());
        if(!TextUtils.isEmpty(makeupResponse.getDescription())) {
            holder.tvDesc.setVisibility(View.VISIBLE);
            holder.tvDesc.setText(makeupResponse.getDescription());
            holder.tvDesc.setShowingLine(2);
            holder.tvDesc.addShowMoreText("Read More");
            holder.tvDesc.addShowLessText("Less");
            holder.tvDesc.setShowMoreColor(Color.RED);
            holder.tvDesc.setShowLessTextColor(Color.RED);
        }

        Glide.with(context)
                .load(makeupResponse.getImage_link())
                .error(R.drawable.no_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v ->
                ProductDetailsActivity.startProductDetailsActivity(context,makeupResponse));
    }

    @Override
    public int getItemCount() {
        return makeupResponseList.size();
    }

    public static class MakepListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvBrand;
        private ShowMoreTextView tvDesc;
        private AppCompatImageView productImage;
        private ProgressBar progressBar;

        public MakepListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            productImage = itemView.findViewById(R.id.productImage);
            progressBar = itemView.findViewById(R.id.progress);

        }
    }

}