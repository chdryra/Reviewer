/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.TreeMethods.Interfaces;


import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;

/**
 * Visitor pattern for {@link ReviewNode}s specifically
 * targeted towards rating calculations.
 */
public interface VisitorRatingCalculator extends VisitorReviewNode {
    //abstract
    float getRating();

    int getWeight();
}
