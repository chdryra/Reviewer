/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Expands on {@link ControllerReview} to include the {@link ReviewNode} interface.
 */
class ControllerReviewNode extends ControllerReview<ReviewNode> {
    private ControllerReviewCollection<ReviewNode> mChildrenController;

    ControllerReviewNode(ReviewNode node) {
        super(node);
    }

    ControllerReviewCollection<ReviewNode> createChildrenController() {
        return new ControllerReviewCollection<ReviewNode>(getControlledReview().getChildren());
    }

    ControllerReviewCollection<ReviewNode> getChildrenController() {
        if (mChildrenController == null) mChildrenController = createChildrenController();
        return mChildrenController;
    }

    @Override
    boolean hasData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            return getControlledReview().getChildren().size() > 0;
        } else {
            return super.hasData(dataType);
        }
    }

    @Override
    GVReviewDataList getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            return getChildrenController().toGridViewable();
        } else {
            return super.getData(dataType);
        }
    }

    boolean isReviewRatingAverage() {
        return getControlledReview().isRatingIsAverageOfChildren();
    }

    void setReviewRatingAverage(boolean isAverage) {
        getControlledReview().setRatingIsAverageOfChildren(isAverage);
    }
}
