/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Null visitor for pure traversal information, for example to build a depth map etc.
 */
class VisitorNull implements VisitorReviewNode {

    @Override
    public void visit(ReviewNode reviewNode) {
        //Used for traversal information
    }

}
