/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ObjectHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Controls editing of review trees in the android activities/fragments layer (the View layer in an
 * MVC framework).
 * <p/>
 * <p>
 * Holds 2 objects:
 * <ul>
 * <li>Node controller: controls access to the review-in-progress node model</li>
 * <li>Collection of sub-review controllers: There maybe multiple controllers associated
 * with the potential tree structure of the review in progress that may need to be passed
 * back and forth between activities.
 * </li>
 * </ul>
 * </p>
 *
 * @see ControllerReview
 * @see ControllerReviewNodeExpandable
 */
public class ControllerReviewTreeEditable extends ControllerReviewEditable {
    private static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";

    private final ControllerReviewNodeExpandable mReviewNodeExpandable;
    private final ObjectHolder                   mControllers;

    ControllerReviewTreeEditable() {
        super(FactoryReview.createReviewInProgress());
        mReviewNodeExpandable = new ControllerReviewNodeExpandable(getReviewNodeExpandable());
        mControllers = new ObjectHolder();
    }

    @Override
    ControllerReviewNode getReviewNode() {
        return mReviewNodeExpandable;
    }

    @Override
    GVReviewDataList<? extends ViewHolderData> getData(GVReviewDataList.GVType dataType) {
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            return mReviewNodeExpandable.getData(dataType);
        } else {
            return super.getData(dataType);
        }
    }

    ReviewNode publishAndTag(PublisherReviewTree publisher) {
        ReviewNode finalReview = publisher.publish(getReviewNodeExpandable());
        for (ReviewNode node : finalReview.flattenTree()) {
            TagsManager.tag(node.getReview(), (GVTagList) getData(GVReviewDataList.GVType.TAGS));
        }

        return finalReview;
    }

    @Override
    <D extends GVReviewDataList<? extends ViewHolderData>> void setData(D data) {
        GVReviewDataList.GVType dataType = data.getGVType();
        if (dataType == GVReviewDataList.GVType.CHILDREN) {
            mReviewNodeExpandable.setChildren((GVReviewSubjectRatingList) data);
        } else {
            super.setData(data);
        }
    }

    Bundle pack(ControllerReview controller) {
        Bundle args = new Bundle();
        args.putString(CONTROLLER_ID, controller.getId());
        register(controller);
        return args;
    }

    ControllerReview unpack(Bundle args) {
        ControllerReview controller = args != null ? getControllerFor(args.getString
                (CONTROLLER_ID)) : null;
        unregister(controller);

        return controller;
    }

    void pack(ControllerReview controller, Intent i) {
        i.putExtra(CONTROLLER_ID, controller.getId());
        register(controller);
    }

    private ReviewNodeExpandable getReviewNodeExpandable() {
        return (ReviewNodeExpandable) getControlledReview();
    }

    private void register(ControllerReview controller) {
        mControllers.addObject(controller.getId(), controller);
    }

    private ControllerReview getControllerFor(String id) {
        return getController(id);
    }

    private void unregister(ControllerReview controller) {
        if (controller != null) {
            mControllers.removeObject(controller.getId());
        }
    }

    private ControllerReview getController(String id) {
        return (ControllerReview) mControllers.getObject(id);
    }
}
