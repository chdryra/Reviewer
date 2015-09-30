/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewMaker;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewsManager;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderToData<T extends GvData> implements GridDataExpander<T> {
    private ReviewViewAdapter<? extends GvData> mParent;
    private GvDataCollection<T> mData;
    private Context mContext;

    public ExpanderToData(Context context, ReviewViewAdapter<? extends GvData> parent) {
        mContext = context;
        mParent = parent;
    }

    @Override
    public boolean isExpandable(T datum) {
        if (mData == null) return false;
        return datum.hasElements() && mData.contains(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridCell(T datum) {
        if (isExpandable(datum)) {
            return FactoryReviewViewAdapter.newExpandToDataAdapter(mContext, mParent,
                    (GvDataCollection<? extends GvData>) datum);
        }

        return null;
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandGridData() {
        IdableList<Review> nodes = new IdableList<>();
        for (int i = 0; i < mData.size(); ++i) {
            ReviewNode node = getReviewNode(mData.getItem(i));
            if (node != null) nodes.add(node);
        }

        ReviewNode meta = ReviewMaker.createMetaReview(mContext, nodes, mData.getStringSummary());
        return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, meta);
    }

    @Override
    public void setData(GvDataCollection<T> data) {
        mData = data;
    }

    private ReviewNode getReviewNode(T item) {
        if (item.isCollection() && !item.hasElements()) return null;
        return ReviewsManager.getReview(mContext, item);
    }
}
