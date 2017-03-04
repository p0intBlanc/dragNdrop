package com.code.shetty.playment;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.code.shetty.playment.databinding.RecyclerItemBinding;

import java.util.List;

/**
 * Created by shetty on 3/4/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ImageItem> mSource;
    OnRecyclerItemClicked onImageClickListener;
    Context mcontext;


    public GalleryAdapter(List<ImageItem> source,OnRecyclerItemClicked listener) {
        this.mSource = source;
        this.onImageClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        mcontext = parent.getContext();
        RecyclerItemBinding binding = RecyclerItemBinding.inflate(inflater, parent, false);

        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        Glide.with(mcontext).load(mSource.get(position).imageId).asBitmap().into(myHolder.binding.image);
        myHolder.binding.image.setOnLongClickListener(new MyLongClickListener());
        myHolder.binding.image.setTag(mSource.get(position).imageId);
    }

    @Override
    public int getItemCount() {
        return mSource.size();
    }




    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        RecyclerItemBinding binding;

        public MyHolder(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onImageClickListener.onClick((Integer) view.getTag());
        }
    }

    private final class MyLongClickListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            return false;
        }
    }

    public interface OnRecyclerItemClicked {
        void onClick(int resId);
    }
}
