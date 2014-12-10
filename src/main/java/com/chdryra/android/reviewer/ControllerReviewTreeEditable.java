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
    public VgDataList getData(VgDataList.GvType dataType) {
        return dataType == VgDataList.GvType.CHILDREN ?
                mReviewNodeExpandable.getData(dataType) : super.getData(dataType);
    }

    @Override
    public void setData(VgDataList data) {
        if (data.getGvType() == VgDataList.GvType.CHILDREN) {
            mReviewNodeExpandable.setChildren((VgSubjectRatingList) data);
        } else {
            super.setData(data);
        }
    }

    ReviewNode publishAndTag(PublisherReviewTree publisher) {
        ReviewNode finalReview = publisher.publish(getReviewNodeExpandable());
        VgTagList tags = (VgTagList) getData(VgDataList.GvType.TAGS);
        for (ReviewNode node : finalReview.flattenTree()) {
            TagsManager.tag(node.getReview(), tags);
        }

        return finalReview;
    }

    private ReviewNodeExpandable getReviewNodeExpandable() {
        return (ReviewNodeExpandable) getControlledReview();
    }
}
