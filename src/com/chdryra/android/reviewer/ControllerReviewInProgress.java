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

import com.chdryra.android.mygenerallibrary.IntentObjectHolder;

/**
 * Controls editing of new reviews in the android activities/fragments layer (the View layer in an
 * MVC framework).
 *
 * <p>
 *     Holds 2 objects:
 *     <ul>
 *         <li>Review controller: controls access to the review-in-progress model data</li>
 *         <li>Collection of sub-review controllers: There maybe multiple controllers associated
 *         with the potential tree structure of the review in progress that may need to be passed
 *         back and forth between activities.
 *         </li>
 *     </ul>
 * </p>
 *
 * @see ControllerReview
 * @see ControllerReviewNodeExpandable
 */
class ControllerReviewInProgress {
    private static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";

    private final ControllerReviewNodeExpandable mReviewInProgress;
    private final IntentObjectHolder             mControllers;

    ControllerReviewInProgress() {
        mReviewInProgress = new ControllerReviewNodeExpandable(FactoryReview
                .createReviewInProgress());
        mControllers = new IntentObjectHolder();
    }

    ControllerReviewNodeExpandable getReviewInProgress() {
        return mReviewInProgress;
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

    private void register(ControllerReview controller) {
        mControllers.addObject(controller.getId(), controller);
    }

    private ControllerReview getControllerFor(String id) {
        return get(id);
    }

    private void unregister(ControllerReview controller) {
        if (controller != null) {
            mControllers.removeObject(controller.getId());
        }
    }

    private ControllerReview get(String id) {
        return (ControllerReview) mControllers.getObject(id);
    }
}
