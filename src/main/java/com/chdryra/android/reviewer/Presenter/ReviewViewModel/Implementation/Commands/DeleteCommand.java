/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleterCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DeleteCommand extends Command implements AlertListener {
    private CurrentScreen mScreen;
    private ReviewDeleter mDeleter;

    public DeleteCommand(int requestCode,
                         ExecutionListener listener,
                         CurrentScreen screen,
                         ReviewDeleter deleter) {
        super(requestCode, listener);
        mScreen = screen;
        mDeleter = deleter;
    }

    @Override
    public void execute() {
        mScreen.showDeleteConfirm(Strings.Alerts.DELETE_REVIEW, getRequestCode(), this);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == getRequestCode()) deleteReview();
    }

    private void deleteReview() {
        mScreen.showToast(Strings.Toasts.DELETING);
        mDeleter.deleteReview(new ReviewDeleterCallback() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
                mScreen.showToast(result.getMessage());
                onExecutionComplete();
            }
        });
    }
}
