/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DialogLayout;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogLayoutBasic<T extends GvData> implements DialogLayout<T> {
    private final LayoutHolder mHolder;

    public DialogLayoutBasic(LayoutHolder holder) {
        mHolder = holder;
    }

    @Override
    public void initialise(T data) {
        updateLayout(data);
    }

    @Override
    public View createLayoutUi(Context context, T data) {
        mHolder.inflate(context);
        initialise(data);
        return mHolder.getView();
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {

    }

    @Override
    public View getView(int viewId) {
        return mHolder.getView(viewId);
    }
}
