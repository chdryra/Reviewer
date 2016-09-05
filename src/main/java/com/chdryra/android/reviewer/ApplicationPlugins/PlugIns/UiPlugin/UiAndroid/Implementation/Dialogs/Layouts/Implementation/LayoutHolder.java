/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Concerned with inflating layouts and
 * holding Views on some {@link GvData}.
 */
class LayoutHolder {
    private final int mLayout;
    private final Integer[] mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;
    private View mInflated;

    public LayoutHolder(int layoutId, Integer...viewIds) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<>(mUpdateableViewIds.length);
    }

    public View getView() {
        return mInflated;
    }

    public void inflate(Context context) {
        mInflated = android.view.LayoutInflater.from(context).inflate(mLayout, null);
        if (mInflated != null) {
            for (int viewId : mUpdateableViewIds) {
                mUpdateableViews.put(viewId, mInflated.findViewById(viewId));
            }
        }
    }

    public View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
