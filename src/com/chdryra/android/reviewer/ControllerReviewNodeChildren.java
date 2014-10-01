/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

class ControllerReviewNodeChildren extends ControllerReviewNodeCollection {
    private final ReviewNode mParent;

    public ControllerReviewNodeChildren(ReviewNode parentNode) {
        super(parentNode.getChildren());
        mParent = parentNode;
    }

    public void setChildren(GVReviewSubjectRatingList children) {
        removeAll();
        addChildren(children);
    }

    void removeAll() {
        mParent.clearChildren();
    }

    void addChildren(GVReviewSubjectRatingList children) {
        for (GVReviewSubjectRating child : children) {
            addChild(child.getSubject(), child.getRating());
        }
    }

    void addChild(String title, float rating) {
        ReviewEditable r = FactoryReview.createReviewInProgress(title);
        r.setRating(rating);
        mParent.addChild(r);
    }
}
