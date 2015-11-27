/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 June, 2015
 */

package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Interfaces.DialogLayout;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogLayoutBasic<T extends GvData> implements DialogLayout<T> {
    private final LayoutHolder mHolder;

    //Constructors
    public DialogLayoutBasic(int layoutId, int[] viewIds) {
        mHolder = new LayoutHolder(layoutId, viewIds);
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

    /**
     * Concerned with inflating layouts and
     * holding Views on some {@link GvData}.
     */
    private static class LayoutHolder {
        private final int mLayout;
        private final int[] mUpdateableViewIds;
        private final SparseArray<View> mUpdateableViews;
        private View mInflated;

        //Constructors
        private LayoutHolder(int layoutId, int[] viewIds) {
            mLayout = layoutId;
            mUpdateableViewIds = viewIds;
            mUpdateableViews = new SparseArray<>(mUpdateableViewIds.length);
        }

        //public methods
        private View getView() {
            return mInflated;
        }

        private void inflate(Context context) {
            mInflated = android.view.LayoutInflater.from(context).inflate(mLayout, null);
            if (mInflated != null) {
                for (int viewId : mUpdateableViewIds) {
                    mUpdateableViews.put(viewId, mInflated.findViewById(viewId));
                }
            }
        }

        private final View getView(int viewId) {
            return mUpdateableViews.get(viewId);
        }
    }
}
