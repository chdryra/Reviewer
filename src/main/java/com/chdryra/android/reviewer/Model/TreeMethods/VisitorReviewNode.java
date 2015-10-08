/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.TreeMethods;

import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Visitor pattern for {@link ReviewNode}s
 */
public interface VisitorReviewNode {
    //abstract methods
    //abstract
    void visit(ReviewNode reviewNode);
}
