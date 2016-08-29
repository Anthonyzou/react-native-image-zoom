package com.image.zoom;
import android.support.annotation.IntDef;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by azou on 25/02/16.
 */
class ImageEvent extends Event<ImageEvent> {


/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */


    @IntDef({ON_TAP, ON_LOAD, ON_SCALE, ON_MATRIX})
    @Retention(RetentionPolicy.SOURCE)
    @interface ImageEventType {}

    public static final int ON_TAP = 1;
    public static final int ON_LOAD = 2;
    public static final int ON_SCALE = 3;
    public static final int ON_MATRIX = 4;

    private final int mEventType;

    public ImageEvent(int viewId, long timestampMs, @ImageEventType int eventType) {
        super(viewId);
        mEventType = eventType;
    }

    public static String eventNameForType(@ImageEventType int eventType) {
        switch(eventType) {
            case ON_TAP:
                return "topTap";
            case ON_LOAD:
                return "topLoad";
            case ON_SCALE:
                return "topScale";
            case ON_MATRIX:
                return "topMatrix";
            default:
                throw new IllegalStateException("Invalid image event: " + Integer.toString(eventType));
        }
    }

    @Override
    public String getEventName() {
        return ImageEvent.eventNameForType(mEventType);
    }

    @Override
    public short getCoalescingKey() {
        // Intentionally casting mEventType because it is guaranteed to be small
        // enough to fit into short.
        return (short) mEventType;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), map);
    }

    private WritableMap map = null;
    public ImageEvent setExtras(WritableMap map){
        this.map = map;
        return this;
    }
}
