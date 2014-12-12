/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Implementation of {@link GvDataUiHolder} for Dialogs. Mainly
 * concerned with inflating layouts and holding Views. Uses a
 * {@link DialogHolderUI} to conform to the {@link com.chdryra
 * .android.reviewer.GVReviewDataUI} part of the {@link com.chdryra.android.reviewer
 * .ReviewDataUIHolder} interface.
 * <p>
 * Need to override {@link #getGvDataUi()} which returns a
 * {@link DialogHolderUI}. This is a helper class that provides
 * a link between the review data-specific UI, and the actual Dialog window itself.
 * </p>
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
public abstract class DialogHolder<T extends GvDataList.GvData> implements GvDataUiHolder<T> {
    private final int               mLayout;
    private final int[]             mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;
    private       View              mInflated;

    protected abstract GvDataUi<T> getGvDataUi();

    public DialogHolder(int layoutId, int[] viewIds) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<>(mUpdateableViewIds.length);
    }

    @Override
    public void inflate(Context context) {
        mInflated = LayoutInflater.from(context).inflate(mLayout, null);
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
        getGvDataUi().initialiseView(data);
    }

    @Override
    public void updateView(T data) {
        getGvDataUi().updateView(data);
    }

    @Override
    public T getGvData() {
        return getGvDataUi().getGvData();
    }

    final View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
