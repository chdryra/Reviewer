/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterGridData extends ReviewViewAdapterBasic {
    private Context           mContext;
    private ReviewViewAdapter mParentAdapter;

    public AdapterGridData(Context context, ReviewViewAdapter parent, GridDataWrapper wrapper) {
        mContext = context;
        mParentAdapter = parent;
        setWrapper(wrapper);
        setExpander(new ExpanderGridCell(mContext, this));
    }

    @Override
    public String getSubject() {
        return mParentAdapter.getSubject();
    }

    @Override
    public float getRating() {
        return mParentAdapter.getRating();
    }

    @Override
    public float getAverageRating() {
        return mParentAdapter.getAverageRating();
    }

    @Override
    public GvImageList getCovers() {
        GvDataList gridData = getGridData();
        if (gridData.hasReviewId()) {
            return CoversManager.getCovers(mContext, gridData.getReviewId());
        } else {
            return mParentAdapter.getCovers();
        }
    }
}
