/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataAdapter extends ReviewViewAdapterBasic {
    private Context           mContext;
    private ReviewViewAdapter mParentAdapter;
    private GvDataList        mData;

    public ReviewDataAdapter(Context context, ReviewViewAdapter parent, GvDataList gridData) {
        mContext = context;
        mParentAdapter = parent;
        mData = gridData;
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
    public GvDataList getGridData() {
        return mData;
    }

    @Override
    public GvImageList getCovers() {
        if (mData.hasHoldingReview()) {
            return CoversManager.getCovers(mContext, mData.getHoldingReviewId());
        } else {
            return mParentAdapter.getCovers();
        }
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mData.contains(datum) && datum.isList() && ((GvDataList) datum).size() > 0;
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return isExpandable(datum) ? new ReviewDataAdapter(mContext, this, (GvDataList) datum) :
                null;
    }
}
