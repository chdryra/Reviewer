/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogLayout<T extends GvData> {
    private final LayoutHolder mHolder;

    public abstract void updateLayout(T data);

    public DialogLayout(int layoutId, int[] viewIds) {
        mHolder = new LayoutHolder(layoutId, viewIds);
    }

    public void initialise(T data) {
        updateLayout(data);
    }

    public View createLayoutUi(Context context, T data) {
        mHolder.inflate(context);
        initialise(data);
        return mHolder.getView();
    }

    public void onActivityAttached(Activity activity, Bundle args) {

    }

    public View getView(int viewId) {
        return mHolder.getView(viewId);
    }
}
