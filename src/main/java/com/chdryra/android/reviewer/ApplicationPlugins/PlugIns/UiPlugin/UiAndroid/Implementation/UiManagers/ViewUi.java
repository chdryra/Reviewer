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

public class ViewUi<V extends View, W> {
    private final V mView;
    private final ValueGetter<W> mGetter;

    public ViewUi(V view, ValueGetter<W> getter) {
        mView = view;
        mGetter = getter;
    }

    public V getView() {
        return mView;
    }

    public W getValue() {
        return mGetter.getValue();
    }

    public interface ValueGetter<T> {
        T getValue();
    }
}
