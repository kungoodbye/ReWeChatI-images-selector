package com.example.rewechati_images_selector;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rewechati_images_selector.model.ImageItem;
import com.example.rewechati_images_selector.model.ImageListContent;
import com.example.rewechati_images_selector.utilities.DraweeUtils;
import com.example.rewechati_images_selector.utilities.FileUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zfdang.multiple_images_selector.R;

import java.io.File;
import java.util.List;

/**
 * Created by 黄焜 on 2017/4/12.
 */

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder> {

    private final List<ImageItem> mValues;
    private final OnImageRecyclerViewInteractionListener mListener;
    private static final String TAG = "ImageAdapter";

        private Context mContext;

    public ImageRecyclerViewAdapter(List<ImageItem> items, OnImageRecyclerViewInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_image_item, parent, false);
        mContext=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ImageItem imageItem = mValues.get(position);
        holder.mItem = imageItem;
        Uri newURI;
        //无相机
        if (!imageItem.isCamera()) {
            // draw image first

            File imageFile = new File(imageItem.path);
            if (imageFile.exists()) {
                newURI = Uri.fromFile(imageFile);
            } else {
                newURI = FileUtils.getUriByResId(R.mipmap.default_image);
            }
           //用Glide
            Glide.with(mContext).load(newURI).asBitmap().into(holder.mDrawee);
            //用Fresco展示略缩图
//            DraweeUtils.showThumb(newURI, holder.mDrawee);

            holder.mImageName.setVisibility(View.GONE);
            holder.mChecked.setVisibility(View.VISIBLE);
            if (ImageListContent.isImageSelected(imageItem.path)) {
                holder.mMask.setVisibility(View.VISIBLE);
                holder.mChecked.setImageResource(R.mipmap.image_selected);
            } else {
                holder.mMask.setVisibility(View.GONE);
                holder.mChecked.setImageResource(R.mipmap.image_unselected);
            }
        } else {
            // camera icon, not normal image
            newURI = FileUtils.getUriByResId(R.mipmap.ic_photo_camera_white_48dp);
            //用Glide加载图片，gif显示第一帧
            Glide.with(mContext).load(newURI).asBitmap().into(holder.mDrawee);
            //用Fresco
//            DraweeUtils.showThumb(newURI, holder.mDrawee);

            holder.mImageName.setVisibility(View.VISIBLE);
            holder.mChecked.setVisibility(View.GONE);
            holder.mMask.setVisibility(View.GONE);
        }

        //点击选框
        holder.mChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(TAG, "onClick: " + holder.mItem.toString());
                if(!holder.mItem.isCamera()) {
                    if(!ImageListContent.isImageSelected(imageItem.path)) {
                        // 只选一张新图片, 确认总图片数量
                        if(ImageListContent.SELECTED_IMAGES.size() < SelectorSettings.mMaxImageNumber) {
                            ImageListContent.toggleImageSelected(imageItem.path);
                            notifyItemChanged(position);
                        } else {
                            // set flag
                            ImageListContent.bReachMaxNumber = true;
                        }
                    } else {
                        // 反选
                        ImageListContent.toggleImageSelected(imageItem.path);
                        notifyItemChanged(position);
                    }
                } else {
                    // do nothing here, listener will launch camera to capture image
                }
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onImageItemInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final SimpleDraweeView mDrawee;
        public final ImageView mChecked;
        public final View mMask;
        public ImageItem mItem;
        public TextView mImageName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDrawee = (SimpleDraweeView) view.findViewById(R.id.image_drawee);
            assert mDrawee != null;
            mMask = view.findViewById(R.id.image_mask);
            assert mMask != null;
            mChecked = (ImageView) view.findViewById(R.id.image_checked);
            assert mChecked != null;
            mImageName = (TextView) view.findViewById(R.id.image_name);
            assert mImageName != null;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
