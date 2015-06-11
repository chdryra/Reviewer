/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 12 May, 2015
 */

package com.chdryra.android.reviewer.Controller.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ExpanderChildren extends ExpanderChildNode {
    public ExpanderChildren(Context context, ViewerChildList source) {
        super(context, source);
    }

    @Override
    public ReviewViewAdapter expandItem(GvData datum) {
        if (isExpandable(datum)) {
            GvReviewList.GvReviewOverview overview = (GvReviewList.GvReviewOverview) datum;
            ReviewId id = ReviewId.fromString(overview.getId());
            ReviewIdableList<ReviewNode> children = getChildrenWrapper().getNode().getChildren();
            if (children.containsId(id)) {
                ReviewNode child = children.get(id);
                ViewerChildList wrapper = new ViewerChildList(child);
                GridDataExpander expander = new ExpanderChildren(getContext(), wrapper);

                return new AdapterReviewNode(child, wrapper, expander);
            }
        }

        return null;
    }
}
