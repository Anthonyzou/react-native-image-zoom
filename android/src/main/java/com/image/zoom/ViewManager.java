package com.image.zoom;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;

import com.facebook.react.uimanager.events.EventDispatcher;

import java.util.Map;

import javax.annotation.Nullable;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by azou on 15/02/16.
 */
public class ViewManager extends SimpleViewManager<PhotoView> {
    private PhotoView photoView;
    private EventDispatcher mEventDispatcher;
    private Float initScale = 1.0f;

    public ViewManager() {
    }

    @Override
    public String getName() {
        return "ImageViewZoom";
    }


    @Override
    public PhotoView createViewInstance(ThemedReactContext reactContext) {
        photoView = new PhotoView(reactContext);
        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        return photoView;
    }

    @ReactProp(name = "source")
    public void setSource(final PhotoView view, @Nullable ReadableMap source) {
        if (source != null && source.hasKey("uri")) {
            String data = source.getString("uri");
            Glide
                .with(view.getContext())
                .load(data)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        view.setScale(initScale, true);
                        return false;
                    }
                })
                .into(view)

            ;

            view.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    mEventDispatcher.dispatchEvent(
                            new ImageEvent(view.getId(), SystemClock.uptimeMillis(), ImageEvent.ON_TAP)
                    );
                }
            });
        }
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                ImageEvent.eventNameForType(ImageEvent.ON_TAP),
                MapBuilder.of("registrationName", "onTap")
        );
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
    public void setScale(PhotoView view, @Nullable float scale) {
        initScale = scale;
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
        
        photoView.setScaleType(value);
    }

}
