/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildNode implements GridCellExpander<GvReviewOverviewList.GvReviewOverview> {
    private Context          mContext;
    private ReviewNode mParent;

    public ExpanderChildNode(Context context, ReviewNode parent) {
        mContext = context;
        mParent = parent;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public boolean isExpandable(GvReviewOverviewList.GvReviewOverview datum) {
        ReviewId id = ReviewId.fromString(datum.getId());
        return mParent.getChildren().containsId(id);
    }

    @Override
    public ReviewViewAdapter expandItem(GvReviewOverviewList.GvReviewOverview datum) {
        if (isExpandable(datum)) {
            ReviewIdableList<ReviewNode> nodes = mParent.getChildren();
            ReviewNode node = nodes.get(ReviewId.fromString(datum.getId()));

            return FactoryReviewViewAdapter.newTreeDataAdapter(mContext, node);
        }

        return null;
    }

    protected ReviewNode getParent() {
        return mParent;
    }
}
