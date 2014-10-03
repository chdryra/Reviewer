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
 * Controls editing of new reviews. Holds 2 objects:
 * <ul>
 *     <li>Review controller: controls access to the review in progress</li>
 *     <li>Collection of controllers being passed back and forth between activities: There
 *     maybe multiple controllers associated with the potential tree structure of the review in
 *     progress that may need to be passed back and forth between activities.
 *     </li>
 * </ul>
 */
class Controller {
    private static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";
    private final ControllerReviewNodeExpandable mReviewInProgress;
    private final IntentObjectHolder             mRNControllers;

    Controller() {
        mReviewInProgress = new ControllerReviewNodeExpandable(FactoryReview
                .createReviewInProgress()
                .getReviewTree());
        mRNControllers = new IntentObjectHolder();
    }

    static RDId convertID(String id) {
        return RDId.generateId(id);
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
        mRNControllers.addObject(controller.getId(), controller);
    }

    private ControllerReview getControllerFor(String id) {
        return get(id);
    }

    private void unregister(ControllerReview controller) {
        if (controller != null) {
            mRNControllers.removeObject(controller.getId());
        }
    }

    private ControllerReview get(String id) {
        return (ControllerReview) mRNControllers.getObject(id);
    }
}
