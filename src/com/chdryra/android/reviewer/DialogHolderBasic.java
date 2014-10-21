/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class DialogHolderBasic<T extends GVReviewDataList.GVReviewData> implements DialogHolder<T> {
    private final int               mLayout;
    private final int[]             mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;

    protected View                                                  mInflated;
    private   DialogUI<T, ? extends DialogCancelActionDoneFragment> mDialogUI;

    protected DialogHolderBasic(int layoutId, int[] viewIds) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<View>(mUpdateableViewIds.length);
    }

    protected void setDialogUI(DialogUI<T, ? extends DialogCancelActionDoneFragment>
                                       dialogUI) {
        mDialogUI = dialogUI;
    }

    @Override
    public void inflate(Activity activity) {
        mInflated = activity.getLayoutInflater().inflate(mLayout, null);
        if (mInflated != null) {
            for (int viewId : mUpdateableViewIds) {
                mUpdateableViews.put(viewId, mInflated.findViewById(viewId));
            }
        }
    }

    @Override
    public View getView() {
        return mInflated;
    }

    @Override
    public void initialiseView(T data) {
        mDialogUI.initialiseView(data);
    }

    @Override
    public void updateView(T data) {
        mDialogUI.updateView(data);
    }

    @Override
    public T getGVData() {
        return mDialogUI.getGVData();
    }

    final View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
