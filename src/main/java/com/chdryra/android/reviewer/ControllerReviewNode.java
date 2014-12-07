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
public class ControllerReviewNode extends ControllerReview<ReviewNode> {
    private ControllerReviewCollection<ReviewNode> mChildrenController;

    ControllerReviewNode(ReviewNode node) {
        super(node);
    }

    @Override
    public boolean hasData(GVReviewDataList.GVType dataType) {
        return dataType == GVReviewDataList.GVType.CHILDREN ? getControlledReview().getChildren()
                .size() > 0 : super.hasData(dataType);
    }

    @Override
    public GVReviewDataList getData(GVReviewDataList.GVType dataType) {
        return dataType == GVReviewDataList.GVType.CHILDREN ? getChildrenController()
                .toGridViewable() : super.getData(dataType);
    }

    ControllerReviewCollection<ReviewNode> createChildrenController() {
        return new ControllerReviewCollection<>(getControlledReview().getChildren());
    }

    ControllerReviewCollection<ReviewNode> getChildrenController() {
        if (mChildrenController == null) mChildrenController = createChildrenController();
        return mChildrenController;
    }

    boolean isReviewRatingAverage() {
        return getControlledReview().isRatingIsAverageOfChildren();
    }

    void setReviewRatingAverage(boolean isAverage) {
        getControlledReview().setRatingIsAverageOfChildren(isAverage);
    }
}
