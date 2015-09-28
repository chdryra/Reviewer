/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 April, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;

/**
 * Created by: Rizwan Choudrey
 * On: 22/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewViewAdapter<T extends GvData> extends ReviewViewAdapterBasic<T> {
    private ReviewViewAdapter mParentAdapter;

    public AdapterReviewViewAdapter(ReviewViewAdapter parent, GridDataViewer<T> wrapper) {
        mParentAdapter = parent;
        setWrapper(wrapper);
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
}
