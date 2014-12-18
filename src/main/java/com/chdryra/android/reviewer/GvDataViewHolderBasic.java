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
 * Implementation of {@link GvDataViewHolder}. Mainly concerned with inflating layouts and
 * holding Views. The {@link GvDataView} part concerned with initialising,
 * updating and extracting review data from views is forwarded to
 * the appropriate {@link GvDataView} object passed in the constructor.
 * </p>
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
public class GvDataViewHolderBasic<T extends GvDataList.GvData> implements GvDataViewHolder<T> {
    private final int               mLayout;
    private final int[]             mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;
    private       View              mInflated;
    private GvDataView<T> mGvDataView;

    public GvDataViewHolderBasic(int layoutId, int[] viewIds, GvDataView<T> gvDataView) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<>(mUpdateableViewIds.length);
        mGvDataView = gvDataView;
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
        mGvDataView.initialiseView(data);
    }

    @Override
    public void updateView(T data) {
        mGvDataView.updateView(data);
    }

    @Override
    public T getGvData() {
        return mGvDataView.getGvData();
    }

    final View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
