package com.image.zoom;

import android.graphics.RectF;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.util.Base64;
import android.util.SparseArray;
import android.view.View;

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
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nullable;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoViewWrapper extends PhotoView {

    private EventDispatcher mEventDispatcher;
    private ReadableMap mParams;
    private SparseArray<Float> scales = new SparseArray<>();
    private ResourceDrawableIdHelper mResourceDrawableIdHelper;
    private boolean mInitialized = false;

    public PhotoViewWrapper(ThemedReactContext context) {
        super(context);
        mEventDispatcher = context.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mResourceDrawableIdHelper = new ResourceDrawableIdHelper();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w > 0 && h > 0) {
            setSource(mParams);
        }
    }

    public void setSource(ReadableMap params) {
        mParams = params;
        @Nullable Uri mUri = null;

        String source = params.hasKey("uri") ? params.getString("uri") : null;
        String thumbnail = params.hasKey("thumbnail") ? params.getString("thumbnail") : null;
        ReadableMap headers = params.hasKey("headers") ? params.getMap("headers") : null;

        //handle base64
        if (source.startsWith("data:image/png;base64,")){
            Glide
                .with(this.getContext())
                .load(Base64.decode(source.replaceAll("data:image\\/.*;base64,", ""), Base64.DEFAULT))
                .into(this)
            ;
            return;
        }

        boolean useStorageFile = false ;

        // handle bundled app resources
        try {
            mUri = Uri.parse(source);
            // Verify scheme is set, so that relative uri (used by static resources) are not handled.
            if (mUri.getScheme() == null) {
                mUri = null;
            } else if(
                !mUri.getScheme().equals("http") &&
                !mUri.getScheme().equals("https")
            ){
                useStorageFile = true ;

                if (!mInitialized) {
                    this.setImageURI(mUri);
                }
            }
        } catch (Exception e) {
            // ignore malformed uri, then attempt to extract resource ID.
        }

        if (mUri == null) {
            mUri = mResourceDrawableIdHelper.getResourceDrawableUri(
                this.getContext(),
                source
            );
            Glide
                .with(this.getContext())
                .load(mUri)
                .into(this);
        } else if (useStorageFile) {
            Glide
                .with(this.getContext())
                .load(mUri)
                .into(this);
        } else {
            // Handle an http / https address
            RequestListener listener = this.getRequestListener();

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
                    .with(this.getContext())
                    .load(new GlideUrl(mUri.toString(), lazyHeaders.build()))
                    .listener(listener);

            //set thumbnails
            if(thumbnail != null) {
                DrawableRequestBuilder<String> thumbnailRequest = Glide
                        .with(this.getContext())
                        .load(thumbnail);
                builder = builder.thumbnail(thumbnailRequest);
            }

            builder.into(this);
        }

        this.setChangeListeners();
    }

    private RequestListener getRequestListener() {

        return new RequestListener<GlideUrl, GlideDrawable>() {
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
                Float scale = scales.get(getId());
                if (scale != null) {
                    setScale(scale, true);
                }

                final int width = getWidth();
                final int height = getHeight();

                WritableMap map = Arguments.createMap();
                map.putInt("height", height);
                map.putInt("width", width);
                mEventDispatcher.dispatchEvent(
                    new ImageEvent(
                        getId(),
                        SystemClock.uptimeMillis(),
                        ImageEvent.ON_LOAD
                    )
                    .setExtras(map)
                );
                return false;
            }
        };
    }

    private void setChangeListeners() {
        if (mInitialized) {
            return;
        }
        mInitialized = true;

        this.setOnScaleChangeListener(new PhotoViewAttacher.OnScaleChangeListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                WritableMap scaleChange = Arguments.createMap();
                scaleChange.putDouble("scaleFactor", scaleFactor);
                scaleChange.putDouble("focusX", focusX);
                scaleChange.putDouble("focusY", focusY);
                mEventDispatcher.dispatchEvent(
                    new ImageEvent(
                        getId(),
                        SystemClock.uptimeMillis(),
                        ImageEvent.ON_SCALE
                    )
                    .setExtras(scaleChange)
                );
            }
        });

        this.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                mEventDispatcher.dispatchEvent(
                    new ImageEvent(
                        view.getId(),
                        SystemClock.uptimeMillis(),
                        ImageEvent.ON_TAP
                    )
                );
            }
        });

        this.setOnMatrixChangeListener(new PhotoViewAttacher.OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rect) {
                WritableMap rectMap = Arguments.createMap();
                rectMap.putDouble("top", rect.top);
                rectMap.putDouble("right", rect.right);
                rectMap.putDouble("bottom", rect.bottom);
                rectMap.putDouble("left", rect.left);
                rectMap.putDouble("height", rect.height());
                rectMap.putDouble("width", rect.width());
                rectMap.putDouble("scale", getScale());
                mEventDispatcher.dispatchEvent(
                    new ImageEvent(
                        getId(),
                        SystemClock.uptimeMillis(),
                        ImageEvent.ON_MATRIX
                    )
                    .setExtras(rectMap)
                );
            }
        });
    }

    public void setCustomScale(@Nullable float scale) {
        scales.put(getId(), scale);
        setScale(scale);
    }

}
