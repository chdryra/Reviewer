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
public class ExpanderChildren extends ExpanderChildNode {
    public ExpanderChildren(Context context, ReviewNode parent) {
        super(context, parent);
    }

    @Override
    public ReviewViewAdapter expandItem(GvReviewOverviewList.GvReviewOverview datum) {
        if (isExpandable(datum)) {
            ReviewId id = ReviewId.fromString(datum.getId());
            ReviewIdableList<ReviewNode> children = getParent().getChildren();
            if (children.containsId(id)) {
                ReviewNode child = children.get(id);
                ExpanderChildren expander = new ExpanderChildren(getContext(), child);
                ViewerChildList wrapper = new ViewerChildList(child, expander);

                return new AdapterReviewNode(child, wrapper);
            }
        }

        return null;
    }
}
