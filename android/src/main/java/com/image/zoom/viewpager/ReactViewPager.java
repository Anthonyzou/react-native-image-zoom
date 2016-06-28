package com.image.zoom.viewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.NativeGestureUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by anthonyou on 2016-06-27.
 */
public class ReactViewPager extends ViewPager {

    private class Adapter extends PagerAdapter {

        private List<View> mViews = new ArrayList<>();

        void addView(View child, int index) {
            mViews.add(index, child);
            notifyDataSetChanged();
            // This will prevent view pager from detaching views for pages that are not currently selected
            // We need to do that since {@link ViewPager} relies on layout passes to position those views
            // in a right way (also thanks to {@link ReactViewPagerManager#needsCustomLayoutForChildren}
            // returning {@code true}). Currently we only call {@link View#measure} and
            // {@link View#layout} after CSSLayout step.

            // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
            // request
            setOffscreenPageLimit(mViews.size());
        }

        void removeViewAt(int index) {
            mViews.remove(index);
            notifyDataSetChanged();

            // TODO(7323049): Remove this workaround once we figure out a way to re-layout some views on
            // request
            setOffscreenPageLimit(mViews.size());
        }

        View getViewAt(int index) {
            return mViews.get(index);
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view, 0, generateDefaultLayoutParams());
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = mViews.get(position);
            container.removeView(view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class PageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mEventDispatcher.dispatchEvent(
                    new PageScrollEvent(getId(), SystemClock.nanoTime(), position, positionOffset));
        }

        @Override
        public void onPageSelected(int position) {
            if (!mIsCurrentItemFromJs) {
                mEventDispatcher.dispatchEvent(
                        new PageSelectedEvent(getId(), SystemClock.nanoTime(), position));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            String pageScrollState;
            switch (state) {
                case SCROLL_STATE_IDLE:
                    pageScrollState = "idle";
                    break;
                case SCROLL_STATE_DRAGGING:
                    pageScrollState = "dragging";
                    break;
                case SCROLL_STATE_SETTLING:
                    pageScrollState = "settling";
                    break;
                default:
                    throw new IllegalStateException("Unsupported pageScrollState");
            }
            mEventDispatcher.dispatchEvent(
                    new PageScrollStateChangedEvent(getId(), SystemClock.nanoTime(), pageScrollState));
        }
    }

    private final EventDispatcher mEventDispatcher;
    private boolean mIsCurrentItemFromJs;
    private boolean mScrollEnabled = true;

    public ReactViewPager(ReactContext reactContext) {
        super(reactContext);
        mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mIsCurrentItemFromJs = false;
        setOnPageChangeListener(new PageChangeListener());
        setAdapter(new Adapter());
    }

    @Override
    public Adapter getAdapter() {
        return (Adapter) super.getAdapter();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mScrollEnabled) {
            return false;
        }
        try {
            if (super.onInterceptTouchEvent(ev)) {
                NativeGestureUtil.notifyNativeGestureStarted(this, ev);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!mScrollEnabled) {
            return false;
        }

        return super.onTouchEvent(ev);
    }

    public void setCurrentItemFromJs(int item, boolean animated) {
        mIsCurrentItemFromJs = true;
        setCurrentItem(item, animated);
        mIsCurrentItemFromJs = false;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        mScrollEnabled = scrollEnabled;
    }

    /*package*/ void addViewToAdapter(View child, int index) {
        getAdapter().addView(child, index);
    }

    /*package*/ void removeViewFromAdapter(int index) {
        getAdapter().removeViewAt(index);
    }

    /*package*/ int getViewCountInAdapter() {
        return getAdapter().getCount();
    }

    /*package*/ View getViewFromAdapter(int index) {
        return getAdapter().getViewAt(index);
    }
}