/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Expands on {@link ControllerReviewNode} to include setters on {@link ReviewNodeExpandable}.
 */
class ControllerReviewNodeExpandable extends ControllerReviewNode {

    ControllerReviewNodeExpandable(ReviewNodeExpandable node) {
        super(node);
    }

    @Override
    ControllerReviewCollection<ReviewNode> createChildrenController() {
        return new ControllerReviewNodeChildren((ReviewNodeExpandable) getControlledReview());
    }

    void setChildren(GVReviewSubjectRatingList children) {
        ((ControllerReviewNodeChildren) getChildrenController()).setChildren(children);
    }

    /**
     * Controls the expansion of the parent {@link ReviewNodeExpandable}.
     */
    static class ControllerReviewNodeChildren extends ControllerReviewCollection<ReviewNode> {
        private final ReviewNodeExpandable mParent;

        ControllerReviewNodeChildren(ReviewNodeExpandable parentNode) {
            super(parentNode.getChildren());
            mParent = parentNode;
        }

        void setChildren(GVReviewSubjectRatingList children) {
            removeAll();
            addChildren(children);
        }

        void removeAll() {
            mParent.clearChildren();
            reinitialise(mParent.getChildren());
        }

        void addChildren(GVReviewSubjectRatingList children) {
            for (GVReviewSubjectRatingList.GVReviewSubjectRating child : children) {
                ReviewEditable r = FactoryReview.createReviewInProgress(child.getSubject());
                r.setRating(child.getRating());
                addReview(mParent.addChild(r));
            }
        }
    }
}
