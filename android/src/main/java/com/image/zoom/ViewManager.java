package com.image.zoom;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
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
    public void setSource(final PhotoView view, ReadableMap params) {
        @Nullable Uri mUri = null;
        String source = params.hasKey("uri") ? params.getString("uri") : null;
        String thumbnail = params.hasKey("thumbnail") ? params.getString("thumbnail") : null;
        ReadableMap headers = params.hasKey("headers") ? params.getMap("headers") : null;

        RequestListener listener = new RequestListener<GlideUrl, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, GlideUrl model,
                                       Target<GlideDrawable> target,
                                       boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, GlideUrl model,
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
                    .into(view)
            ;
        } else {
            // Handle an http address

            // Add http headers
            LazyHeaders.Builder lazyHeaders = new LazyHeaders.Builder();
            Log.d("null headers", String.valueOf(headers != null));
            if(headers != null){
                ReadableMapKeySetIterator it = headers.keySetIterator();
                Log.d("next headers", String.valueOf(it.hasNextKey()));
                while(it.hasNextKey()){
                    String Key = it.nextKey();

                    lazyHeaders.addHeader(Key, headers.getString(Key));
                }
            }

            Log.d("thing", mUri.toString());
            DrawableRequestBuilder builder = Glide
                    .with(view.getContext())
                    .load(new GlideUrl(mUri.toString(), lazyHeaders.build()))
                    .listener(listener)
                    ;

            //set thumbnails
            if(thumbnail != null) {

                DrawableRequestBuilder<String> thumbnailRequest = Glide
                        .with(view.getContext())
                        .load( thumbnail );
                builder = builder.thumbnail(thumbnailRequest);
            }

            builder.into(view);
        }

        view.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                WritableMap scaleChange = Arguments.createMap();
                scaleChange.putDouble("scaleFactor", scaleFactor);
                scaleChange.putDouble("focusX", focusX);
                scaleChange.putDouble("focusY", focusY);
                mEventDispatcher.dispatchEvent(
                        new ImageEvent(view.getId(), SystemClock.uptimeMillis(), ImageEvent.ON_SCALE)
                        .setExtras(scaleChange)
                );
            }
        });

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
                ImageEvent.eventNameForType(ImageEvent.ON_LOAD), MapBuilder.of("registrationName", "onLoad"),
                ImageEvent.eventNameForType(ImageEvent.ON_SCALE), MapBuilder.of("registrationName", "onScaleChange")
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
