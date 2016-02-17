package com.imager;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.ThemedReactContext;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by azou on 15/02/16.
 */
public class ViewManager extends SimpleViewManager<PhotoView> {
  PhotoView attacher;
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
    Picasso.with(view.getContext()).load(source)
        .into(view, new com.squareup.picasso.Callback() {
          @Override
          public void onSuccess() {
          }

          @Override
          public void onError() {
          }
        });
  }

}
