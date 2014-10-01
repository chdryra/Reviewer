/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

public interface ReviewNode extends Review {
    public Review getReview();

    public void setParent(ReviewNode parentNode);

    public void addChild(Review child);

    public void addChild(ReviewNode childNode);

    public RCollectionReviewNode getChildren();

    public void clearChildren();

    public RCollectionReviewNode flatten();

    public boolean isRatingIsAverageOfChildren();

    public void setRatingIsAverageOfChildren(boolean ratingIsAverage);

    public void acceptVisitor(VisitorReviewNode visitorReviewNode);
}
