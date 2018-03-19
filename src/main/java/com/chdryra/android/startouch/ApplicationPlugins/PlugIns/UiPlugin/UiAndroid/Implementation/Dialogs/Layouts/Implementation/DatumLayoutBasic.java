/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Layouts.Implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DatumLayoutBasic<T extends GvData> implements DatumLayoutView<T> {
    private final LayoutHolder mHolder;

    public DatumLayoutBasic(LayoutHolder holder) {
        mHolder = holder;
    }

    protected void onLayoutInflated() {

    }

    @Override
    public void initialise(T data) {
        updateView(data);
    }

    @Override
    public View createLayoutUi(Context context, @Nullable T data) {
        mHolder.inflate(context);
        onLayoutInflated();
        if (data != null) initialise(data);
        return mHolder.getView();
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {

    }

    @Override
    public void onActivityStopped() {

    }

    @Override
    public View getView(int viewId) {
        return mHolder.getView(viewId);
    }
}
