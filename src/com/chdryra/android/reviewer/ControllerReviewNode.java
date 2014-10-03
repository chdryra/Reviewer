/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class ControllerReviewNode extends ControllerReview<ReviewNode> {
    protected ControllerReviewCollection<ReviewNode> mChildrenController;

    public ControllerReviewNode(ReviewNode node) {
        super(node);
        mChildrenController = new ControllerReviewCollection<ReviewNode>(node.getChildren());
    }

    @Override
    public boolean hasData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CRITERIA) {
            return getControlledReview().getChildren().size() > 0;
        } else  {
            return super.hasData(dataType);
        }
    }

    public GVReviewDataList<? extends GVData> getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CRITERIA) {
            return mChildrenController.getGridViewableData();
        } else {
            return super.getData(dataType);
        }
    }

    public boolean isReviewRatingAverage() {
        return getControlledReview().isRatingIsAverageOfChildren();
    }

    //RatingISAverage
    public void setReviewRatingAverage(boolean isAverage) {
        getControlledReview().setRatingIsAverageOfChildren(isAverage);
    }
}
