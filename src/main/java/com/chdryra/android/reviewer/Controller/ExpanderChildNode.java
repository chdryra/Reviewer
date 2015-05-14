/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildNode implements GridDataExpander {
    private Context          mContext;
    private WrapperChildList mChildren;

    public ExpanderChildNode(Context context, WrapperChildList children) {
        mContext = context;
        mChildren = children;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        try {
            GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
            ReviewId id = ReviewId.fromString(overview.getId());
            return mChildren.getNode().getChildren().containsId(id);
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            ReviewIdableList<ReviewNode> nodes = mChildren.getNode().getChildren();
            GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
            ReviewId id = ReviewId.fromString(overview.getId());
            ReviewNode unwrapped = nodes.get(id).getReview().getInternalNode();

            return new AdapterReviewNode.DataAdapter(mContext, unwrapped);
        }

        return null;
    }

    protected WrapperChildList getChildrenWrapper() {
        return mChildren;
    }
}
