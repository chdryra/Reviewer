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
    private final ControllerReviewNode mReviewInProgress;
    private final IntentObjectHolder   mRNControllers;

    Controller() {
        mReviewInProgress = new ControllerReviewNode(FactoryReview.createReviewInProgress().getReviewNode());
        mRNControllers = new IntentObjectHolder();
    }

    static RDId convertID(String id) {
        return RDId.generateId(id);
    }

    ControllerReviewNode getReviewInProgress() {
        return mReviewInProgress;
    }


    Bundle pack(ControllerReviewNode controller) {
        Bundle args = new Bundle();
        args.putString(CONTROLLER_ID, controller.getId());
        register(controller);
        return args;
    }

    ControllerReviewNode unpack(Bundle args) {
        ControllerReviewNode controller = args != null ? getControllerFor(args.getString
                (CONTROLLER_ID)) : null;
        unregister(controller);

        return controller;
    }

    void pack(ControllerReviewNode controller, Intent i) {
        i.putExtra(CONTROLLER_ID, controller.getId());
        register(controller);
    }

    private void register(ControllerReviewNode controller) {
        mRNControllers.addObject(controller.getId(), controller);
    }

    private ControllerReviewNode getControllerFor(String id) {
        return get(id);
    }

    private void unregister(ControllerReviewNode controller) {
        if (controller != null) {
            mRNControllers.removeObject(controller.getId());
        }
    }

    private ControllerReviewNode get(String id) {
        return (ControllerReviewNode) mRNControllers.getObject(id);
    }
}
