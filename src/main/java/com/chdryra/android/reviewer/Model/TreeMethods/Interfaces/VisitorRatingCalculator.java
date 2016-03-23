/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TreeMethods.Interfaces;


import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Visitor pattern for {@link ReviewNode}s specifically
 * targeted towards rating calculations.
 */
public interface VisitorRatingCalculator extends VisitorReviewNode {
    float getRating();

    int getWeight();
}