package com.xingen.glidedemo.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xingen.glidedemo.GlideApp;
import com.xingen.glidedemo.R;
import com.xingen.glidedemo.db.ImageResouce;

/**
 * Created by ${新根} on 2017/5/19 0019.
 * blog: http://blog.csdn.net/hexingen
 */
public class SingleImageFragment extends Fragment {
    private View rootView;
    private ImageView local_iv, remote_iv, preload_iv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_sigleimage, container, false);
        initView();
        startPreload();
        return this.rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLocalImage();
        loadRemoteImage();
        loadPreloadImage();
    }

    /**
     * 预先加载资源
     */
    private void startPreload() {
        GlideApp.with(this).asBitmap()
                .load(ImageResouce.imageResource[1])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload();
    }

    /**
     * 初始化
     */
    private void initView() {
        this.local_iv = (ImageView) this.rootView.findViewById(R.id.sigleiamge_load_iv);
        this.remote_iv = (ImageView) this.rootView.findViewById(R.id.sigleiamge_remote_iv);
        this.preload_iv = (ImageView) this.rootView.findViewById(R.id.sigleiamge_preload_iv);
    }

    /**
     * 加载本地图片，这里是mipmap文件夹下的资源
     */
    private void loadLocalImage() {
        RequestBuilder<Drawable> drawableRequestBuilder = GlideApp.with(this).load(R.mipmap.ic_launcher);
        drawableRequestBuilder.into(this.local_iv);
    }

    /**
     * 从远程网路上加载图片
     */
    private void loadRemoteImage() {
        GlideApp.with(this).asBitmap().load(ImageResouce.imageResource[0]).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(this.remote_iv);
    }

    /**
     * 预先下载原始图片资源，本地加载
     */
    private void loadPreloadImage() {
        GlideApp.with(this).asBitmap().load(ImageResouce.imageResource[1]).diskCacheStrategy(DiskCacheStrategy.ALL).into(this.preload_iv);
    }


}
