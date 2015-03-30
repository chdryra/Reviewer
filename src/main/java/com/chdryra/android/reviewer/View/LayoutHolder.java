/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 20 October, 2014
 */

package com.chdryra.android.reviewer.View;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by: Rizwan Choudrey
 * On: 20/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Concerned with inflating layouts and
 * holding Views on some {@link GvDataList.GvData}.
 */
public class LayoutHolder {
    private final int               mLayout;
    private final int[]             mUpdateableViewIds;
    private final SparseArray<View> mUpdateableViews;
    private       View              mInflated;

    public LayoutHolder(int layoutId, int[] viewIds) {
        mLayout = layoutId;
        mUpdateableViewIds = viewIds;
        mUpdateableViews = new SparseArray<>(mUpdateableViewIds.length);
    }

    public void inflate(Context context) {
        mInflated = android.view.LayoutInflater.from(context).inflate(mLayout, null);
        if (mInflated != null) {
            for (int viewId : mUpdateableViewIds) {
                mUpdateableViews.put(viewId, mInflated.findViewById(viewId));
            }
        }
    }

    public View getView() {
        return mInflated;
    }

    final View getView(int viewId) {
        return mUpdateableViews.get(viewId);
    }
}
