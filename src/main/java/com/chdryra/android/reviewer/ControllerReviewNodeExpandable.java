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
public class ControllerReviewNodeExpandable extends ControllerReviewNode {
    public ControllerReviewNodeExpandable(ReviewNodeExpandable node) {
        super(node);
    }

    public void setChildren(GvSubjectRatingList children) {
        ((ControllerReviewNodeChildren) getChildrenController()).setChildren(children);
    }

    @Override
    ControllerReviewCollection<ReviewNode> createChildrenController() {
        return new ControllerReviewNodeChildren((ReviewNodeExpandable) getControlledReview());
    }

    /**
     * Controls the expansion of the parent {@link ReviewNodeExpandable}.
     */
    private static class ControllerReviewNodeChildren extends
            ControllerReviewCollection<ReviewNode> {
        private final ReviewNodeExpandable mParent;

        private ControllerReviewNodeChildren(ReviewNodeExpandable parentNode) {
            super(parentNode.getChildren());
            mParent = parentNode;
        }

        private void setChildren(GvSubjectRatingList children) {
            removeAll();
            addChildren(children);
        }

        private void removeAll() {
            mParent.clearChildren();
            init(mParent.getChildren());
        }

        private void addChildren(GvSubjectRatingList children) {
            for (GvSubjectRatingList.GvSubjectRating child : children) {
                ReviewEditable r = FactoryReview.createReviewInProgress(child.getSubject());
                r.setRating(child.getRating());
                addReview(mParent.addChild(r));
            }
        }
    }
}
