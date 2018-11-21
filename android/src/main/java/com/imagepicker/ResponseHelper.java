package com.imagepicker;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import java.util.ArrayList;

/**
 * Created by rusfearuth on 24.02.17.
 */

public class ResponseHelper
{
    private ArrayList<Callback> called = new ArrayList<Callback>(3);
    private WritableMap response = Arguments.createMap();

    public void cleanResponse()
    {
        response = Arguments.createMap();
    }

    public @NonNull WritableMap getResponse()
    {
        return response;
    }

    public void putString(@NonNull final String key,
                          @NonNull final String value)
    {
        response.putString(key, value);
    }

    public void putInt(@NonNull final String key,
                       final int value)
    {
        response.putInt(key, value);
    }

    public void putBoolean(@NonNull final String key,
                           final boolean value)
    {
        response.putBoolean(key, value);
    }

    public void putDouble(@NonNull final String key,
                          final double value)
    {
        response.putDouble(key, value);
    }

    public void invokeCustomButton(@NonNull final Callback callback,
                                   @NonNull final String action)
    {
        cleanResponse();
        response.putString("customButton", action);
        invokeResponse(callback);
    }

    public void invokeCancel(@NonNull final Callback callback)
    {
        cleanResponse();
        response.putBoolean("didCancel", true);
        invokeResponse(callback);
    }

    public void invokeError(@NonNull final Callback callback,
                            @NonNull final String error)
    {
        cleanResponse();
        response.putString("error", error);
        invokeResponse(callback);
    }

    public void invokeResponse(@NonNull final Callback callback)
    {
        // TODO: find root issue that causes a bug here
        // callback.invoke cannot be called more than once
        if (called.contains(callback)) return;
        if (called.size() >= 3) called.remove(0);
        called.add(callback);

        callback.invoke(response);
    }
}
