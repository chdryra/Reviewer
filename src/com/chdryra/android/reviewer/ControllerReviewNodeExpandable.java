/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 3 October, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewNodeExpandable extends ControllerReviewNode {
    private final ControllerReviewEditable     mEditableController;

    public ControllerReviewNodeExpandable(ReviewNodeExpandable node) {
        super(node);
        mChildrenController = new ControllerReviewNodeChildren(node);
        Review review = getControlledReview().getReview();
        ReviewEditable editable = review instanceof ReviewEditable ? (ReviewEditable) review :
                FactoryReview.createNullReview();
        mEditableController = new ControllerReviewEditable(editable);
    }

    private ControllerReviewEditable getEditableReview() {
        return mEditableController;
    }

    public ReviewNode publishAndTag(ReviewTreePublisher publisher) {
        ReviewNode finalReview = publisher.publish(getControlledReview());
        for (ReviewNode node : finalReview.flattenTree()) {
            ReviewTagsManager.tag(node.getReview(), getEditableReview().mTagsList);
        }

        return finalReview;
    }

    public GVReviewDataList<? extends GVData> getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.TAGS) {
            //Tags are not set on underlying Review object until publishing stage so still need
            // to retrieve from Editable.
            return getEditableReview().getData(dataType);
        } else {
            return super.getData(dataType);
        }
    }

    <D extends GVReviewDataList<? extends GVData>> void setData(D data) {
        GVReviewDataList.GVType dataType = data.getDataType();
        if (dataType == GVReviewDataList.GVType.CRITERIA) {
            setChildren((GVReviewSubjectRatingList) data);
        } else {
            getEditableReview().setData(data);
        }
    }

    void setRating(float rating) {
        getEditableReview().setRating(rating);
    }

    void setSubject(String subject) {
        getEditableReview().setSubject(subject);
    }

    void setChildren(GVReviewSubjectRatingList children) {
        ((ControllerReviewNodeChildren)mChildrenController).setChildren(children);
    }

    class ControllerReviewNodeChildren extends ControllerReviewCollection<ReviewNode> {
        private final ReviewNodeExpandable mParent;

        public ControllerReviewNodeChildren(ReviewNodeExpandable parentNode) {
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
