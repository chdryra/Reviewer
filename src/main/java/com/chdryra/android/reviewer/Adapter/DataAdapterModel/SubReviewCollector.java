/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorTreeFlattener;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SubReviewCollector {
    private ReviewNode mNode;

    public SubReviewCollector(ReviewNode node) {
        mNode = node;
    }

    public GvChildList collectCriteria() {
        ReviewIdableList<ReviewNode> criteriaNodes = new ReviewIdableList<>();
        for (ReviewNode node : mNode.getChildren()) {
            criteriaNodes.add(collectNodes(node.expand()));
        }

        return MdGvConverter.convertChildren(mNode.expand());
    }

    private ReviewIdableList<ReviewNode> collectNodes(ReviewNode node) {
        return VisitorTreeFlattener.flatten(node);
    }
}
