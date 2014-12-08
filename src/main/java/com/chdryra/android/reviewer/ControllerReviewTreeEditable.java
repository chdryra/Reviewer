/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Controls editing of review trees in the android activities/fragments layer (the View layer in an
 * MVC framework).
 * <p/>
 * <p>
 * Holds Node controller: controls access to the review-in-progress node model</li>
 * </p>
 *
 * @see ControllerReviewNodeExpandable
 */
public class ControllerReviewTreeEditable extends ControllerReviewEditable {
    private final ControllerReviewNodeExpandable mReviewNodeExpandable;

    ControllerReviewTreeEditable(ReviewTreeEditable reviewTree) {
        super(reviewTree);
        mReviewNodeExpandable = new ControllerReviewNodeExpandable(getReviewNodeExpandable());
    }

    @Override
    public ControllerReviewNode getReviewNode() {
        return mReviewNodeExpandable;
    }

    @Override
    public GVReviewDataList getData(GVReviewDataList.GVType dataType) {
        return dataType == GVReviewDataList.GVType.CHILDREN ?
                mReviewNodeExpandable.getData(dataType) : super.getData(dataType);
    }

    @Override
    public void setData(GVReviewDataList data) {
        if (data.getGVType() == GVReviewDataList.GVType.CHILDREN) {
            mReviewNodeExpandable.setChildren((GVReviewSubjectRatingList) data);
        } else {
            super.setData(data);
        }
    }

    ReviewNode publishAndTag(PublisherReviewTree publisher) {
        ReviewNode finalReview = publisher.publish(getReviewNodeExpandable());
        GVTagList tags = (GVTagList) getData(GVReviewDataList.GVType.TAGS);
        for (ReviewNode node : finalReview.flattenTree()) {
            TagsManager.tag(node.getReview(), tags);
        }

        return finalReview;
    }

    private ReviewNodeExpandable getReviewNodeExpandable() {
        return (ReviewNodeExpandable) getControlledReview();
    }
}
