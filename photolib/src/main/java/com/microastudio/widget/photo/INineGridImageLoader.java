package com.microastudio.widget.photo;

import android.content.Context;
import android.widget.ImageView;

/**
 * The interface of imageLoader
 *
 * @author peng
 */

public interface INineGridImageLoader {
    void displayNineGridImage(Context context, String url, ImageView imageView);

    void displayNineGridImage(Context context, String url, ImageView imageView, int width, int height);
}
