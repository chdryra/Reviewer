/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewViewAdapter extends ReviewViewAdapterBasic {
    private ReviewViewAdapter mParentAdapter;
    private GridCellExpander mExpander;

    public AdapterReviewViewAdapter(Context context, ReviewViewAdapter parent, GridDataViewer
            wrapper) {
        mParentAdapter = parent;
        setWrapper(wrapper);
        mExpander = new ExpanderGridCell(context, this);
    }

    public AdapterReviewViewAdapter(ReviewViewAdapter parent, GridDataViewer
            wrapper, GridCellExpander expander) {
        mParentAdapter = parent;
        setWrapper(wrapper);
        mExpander = expander;
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
        return mParentAdapter.getCovers();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mExpander.isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return isExpandable(datum) ? mExpander.expandItem(datum) : null;
    }
}
