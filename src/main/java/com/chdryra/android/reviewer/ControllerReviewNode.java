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
    public ControllerReviewNode(ReviewNode node) {
        super(node);
    }

    @Override
    public boolean hasData(GvDataList.GvType dataType) {
        return dataType == GvDataList.GvType.CHILDREN ? getControlledReview().getChildren()
                .size() > 0 : super.hasData(dataType);
    }

    @Override
    public GvDataList getData(GvDataList.GvType dataType) {
        return dataType == GvDataList.GvType.CHILDREN ? getChildrenController()
                .toGridViewable(false) : super.getData(dataType);
    }

    public boolean isReviewRatingAverage() {
        return getControlledReview().isRatingIsAverageOfChildren();
    }

    ControllerReviewCollection<ReviewNode> createChildrenController() {
        return new ControllerReviewCollection<>(getControlledReview().getChildren());
    }

    ControllerReviewCollection<ReviewNode> getChildrenController() {
        return createChildrenController();
    }
}
