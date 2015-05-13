/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvImageList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewAdapterCollection extends ReviewViewAdapterBasic {
    private ArrayList<ReviewViewAdapter> mAdapters;
    private int mIndex = 0;

    public ReviewViewAdapterCollection(ArrayList<ReviewViewAdapter> adapters) {
        mAdapters = adapters;

    }

    public void nextAdapter() {
        ++mIndex;
        if (mIndex == mAdapters.size()) mIndex = 0;
        notifyGridDataObservers();
    }

    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public float getRating() {
        return getAdapter().getRating();
    }

    @Override
    public float getAverageRating() {
        return getAdapter().getAverageRating();
    }

    @Override
    public GvImageList getCovers() {
        return getAdapter().getCovers();
    }

    @Override
    public GvDataList getGridData() {
        return getAdapter().getGridData();
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return getAdapter().isExpandable(datum);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        return getAdapter().expandItem(datum);
    }

    private ReviewViewAdapter getAdapter() {
        return mAdapters.get(mIndex);
    }
}

