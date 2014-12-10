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

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Implementation of {@link UIHolder} for Dialogs. Mainly
 * concerned with inflating layouts and holding Views. Uses a
 * {@link DialogHolderUI} to conform to the {@link com.chdryra
 * .android.reviewer.GVReviewDataUI} part of the {@link com.chdryra.android.reviewer
 * .ReviewDataUIHolder} interface.
 * <p>
 * Need to override {@link #getGVReviewDataUI()} which returns a
 * {@link DialogHolderUI}. This is a helper class that provides
 * a link between the review data-specific UI, and the actual Dialog window itself.
 * </p>
 *
 * @param <T>: {@link GVDataList.GvData} type.
 */
abstract class DialogHolder<T extends GVDataList.GvData> implements UIHolder<T> {
    private final int               mLayout;
    private final int[]             mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;
    private       View              mInflated;

    protected abstract UIReviewData<T> getGVReviewDataUI();

    DialogHolder(int layoutId, int[] viewIds) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<View>(mUpdateableViewIds.length);
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
        getGVReviewDataUI().initialiseView(data);
    }

    @Override
    public void updateView(T data) {
        getGVReviewDataUI().updateView(data);
    }

    @Override
    public T getGVData() {
        return getGVReviewDataUI().getGVData();
    }

    final View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
