package com.image.zoom;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import javax.annotation.Nullable;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by azou on 15/02/16.
 */
public class ViewManager extends SimpleViewManager<PhotoView> {
    PhotoView attacher;
    ImageLoader imageLoader;

    private Float initScale = 1.0f;

    public ViewManager(ImageLoader instance) {
        this.imageLoader = instance;
    }

    @Override
    public String getName() {
        return "ImageViewZoom";
    }


    @Override
    public PhotoView createViewInstance(ThemedReactContext reactContext) {
        attacher = new PhotoView(reactContext);
        return attacher;
    }

    // In JS this is Image.props.source.uri
    @ReactProp(name = "src")
    public void setSource(PhotoView view, @Nullable String source) {
        imageLoader.displayImage(source, view, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                attacher.setScale(initScale);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
    @ReactProp(name = "tintColor", customType = "Color")
    public void setTintColor(PhotoView view, @Nullable Integer tintColor) {
        if (tintColor == null) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(tintColor);
        }
    }

    @ReactProp(name = "scale")
    public void setScale(PhotoView view, String scale) {
        initScale = Float.parseFloat(scale);
    }

    @ReactProp(name = "scaleType")
    public void setScaleType(PhotoView view, String scaleType) {
        ScaleType value = ScaleType.CENTER;

        switch (scaleType) {
        case "center":
            value = ScaleType.CENTER;
            break;
        case "centerCrop":
            value = ScaleType.CENTER_CROP;
            break;
        case "centerInside":
            value = ScaleType.CENTER_INSIDE;
            break;
        case "fitCenter":
            value = ScaleType.FIT_CENTER;
            break;
        case "fitStart":
            value = ScaleType.FIT_START;
            break;
        case "fitEnd":
            value = ScaleType.FIT_END;
            break;
        case "fitXY":
            value = ScaleType.FIT_XY;
            break;
        case "matrix":
            value = ScaleType.MATRIX;
            break;
        }
        
        attacher.setScaleType(value);
    }    

}
