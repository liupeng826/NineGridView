package com.microastudio.demo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 * @author peng
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, Uri uri, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .asBitmap()
                .load(uri)
                .error(R.drawable.ic_default_image)
                .placeholder(R.drawable.ic_holder_light)
                .apply(new RequestOptions()
                        .override(width, height)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
