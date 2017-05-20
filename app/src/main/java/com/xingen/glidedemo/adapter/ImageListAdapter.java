package com.xingen.glidedemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.xingen.glidedemo.GlideApp;
import com.xingen.glidedemo.R;
import com.xingen.glidedemo.custom.CircleTransform;
import com.xingen.glidedemo.custom.CircularBitmapImageViewTarget;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/19 0019.
 * blog: http://blog.csdn.net/hexingen
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHodler> implements ListPreloader.PreloadModelProvider<String> {
    private List<String> imageList;
    private Context context;
    public ImageListAdapter(Context context,List<String> list,int model){
        this.context=context;
        this.imageList=list;
        this.model=model;
    }
    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView=View.inflate(parent.getContext(), R.layout.item_imagelist_layout,null);
        return new ViewHodler(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
         ImageView imageView=holder.getImageView();
         String url=imageList.get(position);
        if(TextUtils.isEmpty(url)){
            //清空旧数据的引用
            GlideApp.with(context).clear(imageView);
            //当资源为空时候，设置默认图片
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else{//开启一个图片加载
            loadImage(url,imageView);
        }

    }

    /**
     * 加载图片
     * @param url
     * @param imageView
     */
    public void loadImage(String url,ImageView imageView){

       RequestBuilder<Bitmap> bitmapRequestBuilder= GlideApp.with(context)
               .asBitmap()//指定Bitmap类型的RequestBuilder
               .load(url)//网络URL
               .error(R.mipmap.ic_launcher)//异常图片
               .placeholder(R.mipmap.ic_launcher)//占位图片
               .fallback(R.mipmap.ic_launcher);//当url为空时，显示图片
        switch (model){
            case NORMAL:
                bitmapRequestBuilder.into(imageView);
                break;
            case TRANSFORMATION:

                RequestOptions requestOptions=new RequestOptions();
                //在RequestOptions中使用Transformations
                requestOptions.transform(new CircleTransform(context));

                //RequestBuilder<Bitmap> 中添加RequestOptions
                 bitmapRequestBuilder.apply(requestOptions).into(imageView);
                break;
            case IMAGEVIEWTARGET:
                //在RequestBuilder<Bitmap> 中使用自定义的ImageViewTarget
                bitmapRequestBuilder.into(new CircularBitmapImageViewTarget(context,imageView));
                break;


        }
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }



    public static class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHodler(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imagelist_iv);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    private int model;
    public static final int NORMAL = 1;
    /**
     * transformation的标志
     */
    public static final int TRANSFORMATION = 2;
    /**
     * ImageViewTarget的标志
     */
    public static final int IMAGEVIEWTARGET = 3;

    /**
     *  返回中需要需要加载的模型的列表，
     * @param position
     * @return
     */
    @Override
    public List<String> getPreloadItems(int position) {
        return  imageList.subList(position,position+1) ;
    }

    /**
     * 根据上面的列表，生成RequestBuilder
     * @param item
     * @return
     */
    @Override
    public RequestBuilder getPreloadRequestBuilder(String item) {
        return GlideApp.with(context).asBitmap().load(item);
    }
}
