/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public abstract class ViewUi<V extends View, Value> {
    private final V mView;
    private final ValueGetter<Value> mGetter;

    public abstract void update();

    public ViewUi(V view, ValueGetter<Value> getter) {
        mView = view;
        mGetter = getter;
    }

    public V getView() {
        return mView;
    }

    public Value getValue() {
        return mGetter.getValue();
    }

    public interface ValueGetter<T> {
        T getValue();
    }
}
