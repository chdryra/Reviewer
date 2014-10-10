/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Interface for defining a tree traversal algorithm given a node, visitor and node depth (for
 * depth map calculations).
 */
interface TraverserSearchMethod {
    void search(ReviewNode node, VisitorReviewNode visitor, int depth);
}
