package com.imager;

import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.react.uimanager.ThemedReactContext;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by azou on 15/02/16.
 */
public class ViewManager extends SimpleViewManager<ImageView> {
  PhotoViewAttacher attacher;
  ImageView imageView;
  @Override
  public String getName() {
    return "ImageViewZoom";
  }


  @Override
  public ImageView createViewInstance(ThemedReactContext reactContext) {
    imageView = new ImageView(reactContext);
    attacher = new PhotoViewAttacher(imageView);


    return imageView;
  }

  // In JS this is Image.props.source.uri
  @ReactProp(name = "src")
  public void setSource(ImageView view, @Nullable String source) {
    Picasso.with(view.getContext()).load(source)
        .into(imageView, new com.squareup.picasso.Callback() {
          @Override
          public void onSuccess() {
            attacher.update();
          }

          @Override
          public void onError() {

          }
        });
  }

}
