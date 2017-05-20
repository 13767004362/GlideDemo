package com.xingen.glidedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingen.glidedemo.GlideApp;
import com.xingen.glidedemo.R;
import com.xingen.glidedemo.adapter.ImageListAdapter;
import com.xingen.glidedemo.db.ImageResouce;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/19 0019.
 * blog: http://blog.csdn.net/hexingen
 */
public class ImageListFragment extends Fragment {
    private View rootView;
    private RecyclerView recyclerView;
    private TextView title_tv;

    public static ImageListFragment newInstance(Bundle bundle) {
        ImageListFragment fragment = new ImageListFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_imagelist, container, false);
        return this.rootView;
    }

    private int model;
    public static final String MODEL = "model";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.model = getArguments().getInt(MODEL);
        initView();
    }

    private static final int PRELOAD_AHEAD_ITEMS = 5;

    private void initView() {
        this.recyclerView = (RecyclerView) this.rootView.findViewById(R.id.imagelist_recyclerview);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.title_tv = (TextView) this.rootView.findViewById(R.id.imagelist_title);

        this.title_tv.setText(createShowTitle());

        List<String> imageList = Arrays.asList(ImageResouce.imageResource);
        ImageListAdapter imageListAdapter = new ImageListAdapter(getActivity(), imageList, this.model);
        this.recyclerView.setAdapter(imageListAdapter);

        this.recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {//当一个子view被回收时候调用
                ImageListAdapter.ViewHodler viewHodler = (ImageListAdapter.ViewHodler) holder;
                //清除被销毁的ImageView
                GlideApp.with(ImageListFragment.this).clear(viewHodler.getImageView());

            }
        });
    }

    /**
     * 根据model来显示标题。
     * @return
     */
    public String createShowTitle() {
        String title;
        switch (this.model) {

            case ImageListAdapter.IMAGEVIEWTARGET:
                title = "自定义ImageViewTarget实现圆角图片列表";
                break;
            case ImageListAdapter.TRANSFORMATION:
                title = "自定义Transformation实现圆角图片列表";
                break;
            case ImageListAdapter.NORMAL:
            default:
                title = "图片列表";
                break;

        }
        return title;
    }

    @Override
    public void onResume() {
        GlideApp.with(this).resumeRequests();
        super.onResume();
    }

    @Override
    public void onPause() {
        GlideApp.with(this).pauseRequests();
        super.onPause();
    }

    @Override
    public void onLowMemory() {

        GlideApp.with(this).onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        //  GlideApp.with(this).onDestroy();
        super.onDestroy();
    }
}
