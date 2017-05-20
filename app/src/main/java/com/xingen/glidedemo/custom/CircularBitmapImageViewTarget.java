package com.xingen.glidedemo.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by ${新根} on 2017/5/19 0019.
 * blog: http://blog.csdn.net/hexingen
 *
 * 自定义ImageViewTarget去生成一个圆角图片 .
 */
public class CircularBitmapImageViewTarget  extends BitmapImageViewTarget {
    private Context context;
    private ImageView imageView;
    public CircularBitmapImageViewTarget(Context context,ImageView view) {
        super(view);
        this.context=context;
        this.imageView=view;
    }
    /**
     * 重写 setResource（），生成圆角的图片
     * @param resource
     */
    @Override
    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable  bitmapDrawable= RoundedBitmapDrawableFactory.create(this.context.getResources(),resource);
        /**
         *   设置图片的shape为圆形.
         *
         *   若是需要制定圆角的度数，则调用setCornerRadius（）。
         */
        bitmapDrawable.setCircular(true);
        this.imageView.setImageDrawable(bitmapDrawable);
    }
}
