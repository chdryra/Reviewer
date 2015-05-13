/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 May, 2015
 */

package com.chdryra.android.reviewer.Model;

/**
 * Created by: Rizwan Choudrey
 * On: 13/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VisitorTreeFlattener implements VisitorReviewNode {
    ReviewIdableList<ReviewNode> mNodes = new ReviewIdableList<>();

    @Override
    public void visit(ReviewNode node) {
        mNodes.add(node);
        for (ReviewNode child : node.getChildren()) {
            child.acceptVisitor(this);
        }
    }

    public ReviewIdableList<ReviewNode> getNodes() {
        return mNodes;
    }
}
