package com.code.shetty.playment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shetty on 3/4/2017.
 */

public class InteractiveGalleryFragment extends Fragment implements GalleryAdapter.OnRecyclerItemClicked {

    public int imageIds[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5};
    private View mRooot;
    GalleryAdapter mGalleryAdapter;
    RecyclerView mRecyclerList;
    FrameLayout mFrame;
    ImageView mImageShowCase;
    TextView mHintTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRooot = inflater.inflate(R.layout.gallery_layout, container, false);
        mGalleryAdapter = new GalleryAdapter(loadDummyData(), this);
        mRecyclerList = (RecyclerView) mRooot.findViewById(R.id.recyclerList);
        mFrame = (FrameLayout) mRooot.findViewById(R.id.frame);
        mHintTV = (TextView) mRooot.findViewById(R.id.hint);
        mFrame.setOnDragListener(new MyDragListener());
        mImageShowCase = (ImageView) mRooot.findViewById(R.id.showcase);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerList.setHasFixedSize(true);
        mRecyclerList.setAdapter(mGalleryAdapter);
        return mRooot;
    }


    private List<ImageItem> loadDummyData() {
        List<ImageItem> itemsList = new ArrayList<>();
        for (int id : imageIds) {
            ImageItem item = new ImageItem(id);
            itemsList.add(item);
        }

        return itemsList;

    }

    @Override
    public void onClick(int resId) {
        mImageShowCase.setImageResource(resId);
    }


    class MyDragListener implements View.OnDragListener {
// // TODO: 3/4/2017  need to find a way to get a reference to drag shadow class for scaling

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:

                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    mHintTV.setVisibility(View.GONE);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    mHintTV.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    mHintTV.setVisibility(View.GONE);
                    View view = (View) event.getLocalState();
                    int imageResourve = (int) view.getTag();
                     //// TODO: 3/4/2017  handle scaling to preseve aspect ratio instead of using CENTER_CROP
                    Glide.with(getActivity()).load(imageResourve).asBitmap().into(mImageShowCase);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }


}
