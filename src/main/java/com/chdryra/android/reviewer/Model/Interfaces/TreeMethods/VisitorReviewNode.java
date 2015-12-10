/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Model.Interfaces.TreeMethods;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

/**
 * Visitor pattern for {@link ReviewNode}s
 */
public interface VisitorReviewNode {
    void visit(@NonNull ReviewNode reviewNode);
}
