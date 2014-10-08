/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;

/**
 * Expands on ControllerReview to include the ReviewNode interface.
 *
 * @see com.chdryra.android.reviewer.ControllerReview
 * @see com.chdryra.android.reviewer.ReviewNode
 *
 */
class ControllerReviewNode extends ControllerReview<ReviewNode> {
    protected ControllerReviewCollection<ReviewNode> mChildrenController;

    ControllerReviewNode(ReviewNode node) {
        super(node);
        mChildrenController = new ControllerReviewCollection<ReviewNode>(node.getChildren());
    }

    @Override
    boolean hasData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            return getControlledReview().getChildren().size() > 0;
        } else  {
            return super.hasData(dataType);
        }
    }

    @Override
    GVReviewDataList<? extends GVData> getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            return mChildrenController.getGridViewableData();
        } else {
            return super.getData(dataType);
        }
    }

    boolean isReviewRatingAverage() {
        return getControlledReview().isRatingIsAverageOfChildren();
    }

    //RatingISAverage
    public void setReviewRatingAverage(boolean isAverage) {
        getControlledReview().setRatingIsAverageOfChildren(isAverage);
    }
}
