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

    public ControllerReviewTreeEditable(ReviewTreeEditable reviewTree) {
        super(reviewTree);
        mReviewNodeExpandable = new ControllerReviewNodeExpandable(getReviewNodeExpandable());
    }

    @Override
    public ControllerReviewNode getReviewNode() {
        return mReviewNodeExpandable;
    }

    @Override
    public GvDataList getData(GvDataList.GvType dataType) {
        return dataType == GvDataList.GvType.CHILDREN ?
                mReviewNodeExpandable.getData(dataType) : super.getData(dataType);
    }

    @Override
    public void setData(GvDataList data) {
        if (data.getGvType() == GvDataList.GvType.CHILDREN) {
            mReviewNodeExpandable.setChildren((GvChildrenList) data);
        } else {
            super.setData(data);
        }
    }

    public ReviewNode publishAndTag(PublisherReviewTree publisher) {
        ReviewNode finalReview = publisher.publish(getReviewNodeExpandable());
        GvTagList tags = (GvTagList) getData(GvDataList.GvType.TAGS);
        for (ReviewNode node : finalReview.flattenTree()) {
            TagsManager.tag(node.getReview(), tags);
        }

        return finalReview;
    }

    private ReviewNodeExpandable getReviewNodeExpandable() {
        return (ReviewNodeExpandable) getControlledReview();
    }
}
