/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions;

import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiDeleteReview<T extends GvData> extends MenuActionItemBasic<T> implements AlertListener{
    private static int DELETE = RequestCodeGenerator.getCode(MaiDeleteReview.class);
    private final ReviewId mReviewId;

    public MaiDeleteReview(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    @Override
    public void doAction(MenuItem item) {
        if(getParent() != null) {
            getCurrentScreen().showDeleteConfirm(Strings.Alerts.DELETE_REVIEW, DELETE);
        }
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(final int requestCode, Bundle args) {
        if (requestCode == DELETE) {
            getApp().newReviewDeleter(mReviewId).deleteReview(new ReviewDeleter.ReviewDeleterCallback() {
                @Override
                public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
                    if(!result.isError()) {
                        getCurrentScreen().close();
                    } else {
                        getCurrentScreen().showToast(result.getMessage());
                    }
                }
            });
        }
    }
}
