package com.image.zoom;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
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
    private SparseArray<Float> scales = new SparseArray<>();
    private ResourceDrawableIdHelper mResourceDrawableIdHelper;

    public ViewManager() {
        mResourceDrawableIdHelper = new ResourceDrawableIdHelper();
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

    @ReactProp(name = "src")
    public void setSource(final PhotoView view, @Nullable String source) {
        @Nullable Uri mUri = null;
        if (source == null) return;

        RequestListener listener = new RequestListener<String, GlideDrawable>() {
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
                Float scale = scales.get(view.getId());
                if(scale != null) {
                    view.setScale(scale, true);
                }

                final int width = view.getWidth();
                final int height = view.getHeight();

                WritableMap map = Arguments.createMap();
                map.putInt("height", height);
                map.putInt("width", width);
                mEventDispatcher.dispatchEvent(
                        new ImageEvent(view.getId(), SystemClock.uptimeMillis(), ImageEvent.ON_LOAD)
                        .setExtras(map)
                );
                return false;
            }
        };

        //handle base64
        if (source.startsWith("data:image/png;base64,")){
            Glide
                    .with(view.getContext())
                    .load(Base64.decode(source.replaceAll("data:image\\/.*;base64,", ""), Base64.DEFAULT))
                    .listener(listener)
                    .into(view)
            ;
            return;
        }

        // handle bundled app resources
        try {
            mUri = Uri.parse(source);
            // Verify scheme is set, so that relative uri (used by static resources) are not handled.
            if (mUri.getScheme() == null) {
                mUri = null;
            }
        } catch (Exception e) {
            // ignore malformed uri, then attempt to extract resource ID.
        }

        if (mUri == null) {
            mUri = mResourceDrawableIdHelper.getResourceDrawableUri(view.getContext(), source);
            Glide
                    .with(view.getContext())
                    .load(mUri)
                    .listener(listener)
                    .into(view)
            ;
        } else {
            // Handle an http address
            Glide
                    .with(view.getContext())
                    .load(mUri.toString())
                    .listener(listener)
                    .into(view)
            ;
        }

        view.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                mEventDispatcher.dispatchEvent(
                        new ImageEvent(view.getId(), SystemClock.uptimeMillis(), ImageEvent.ON_TAP)
                );
            }
        });
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                ImageEvent.eventNameForType(ImageEvent.ON_TAP), MapBuilder.of("registrationName", "onTap"),
                ImageEvent.eventNameForType(ImageEvent.ON_LOAD), MapBuilder.of("registrationName", "onLoad")
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
        scales.put(view.getId(), scale);
        view.setScale(scale);
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
