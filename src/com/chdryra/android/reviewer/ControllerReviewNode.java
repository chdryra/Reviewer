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
    private final ReviewNode                   mNode;
    private final ControllerReviewEditable     mEditableController;
    private       ControllerReviewNodeChildren mChildrenController;

    public ControllerReviewNode(ReviewNode node) {
        super(node);
        mNode = node;
        Review review = mNode.getReview();
        ReviewEditable editable = review instanceof ReviewEditable ? (ReviewEditable) review :
                FactoryReview.createNullReview();
        mEditableController = new ControllerReviewEditable(editable);
    }

    public ReviewNode publishAndTag(ReviewTreePublisher publisher) {
        ReviewNode finalReview = publisher.publish(mNode);
        for (ReviewNode node : finalReview.flattenTree()) {
            ReviewTagsManager.tag(node.getReview(), mTagsList);
        }

        return finalReview;
    }

    ControllerReviewEditable getEditableReview() {
        return mEditableController;
    }

    public boolean isReviewRatingAverage() {
        return mNode.isRatingIsAverageOfChildren();
    }

    //RatingISAverage
    public void setReviewRatingAverage(boolean isAverage) {
        mNode.setRatingIsAverageOfChildren(isAverage);
    }

    @Override
    public boolean hasData(GVType dataType) {
        if (dataType == GVType.CRITERIA) {
            return getChildrenController().size() > 0;
        } else  {
            return super.hasData(dataType);
        }
    }

    ControllerReviewNodeChildren getChildrenController() {
        if (mChildrenController == null) {
            mChildrenController = new ControllerReviewNodeChildren(mNode);
        }

        return mChildrenController;
    }

    public GVReviewDataList<? extends GVData> getData(GVType dataType) {
        if (dataType == GVType.CRITERIA) {
            return getChildrenController().getGridViewableData();
        } else if (dataType == GVType.TAGS) {
            //Tags are not set on underlying Review object until publishing stage so still need
            // to retrieve from Editable.
            return getEditableReview().getData(dataType);
        } else {
            return super.getData(dataType);
        }
    }

    public <D extends GVReviewDataList<? extends GVData>> void setData(D data) {
        GVType dataType = data.getDataType();
        if (dataType == GVType.CRITERIA) {
            setChildren((GVReviewSubjectRatingList) data);
        } else {
            getEditableReview().setData(data);
        }
    }

    void setChildren(GVReviewSubjectRatingList children) {
        getChildrenController().setChildren(children);
    }

    class ControllerReviewNodeChildren extends ControllerReviewCollection<ReviewNode> {
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
            init(mParent.getChildren());
        }

        void addChildren(GVReviewSubjectRatingList children) {
            for (GVReviewSubjectRatingList.GVReviewSubjectRating child : children) {
                ReviewEditable r = FactoryReview.createReviewInProgress(child.getSubject());
                r.setRating(child.getRating());
                mParent.addChild(r);
            }

            mReviews = mParent.getChildren();
        }
    }
}
