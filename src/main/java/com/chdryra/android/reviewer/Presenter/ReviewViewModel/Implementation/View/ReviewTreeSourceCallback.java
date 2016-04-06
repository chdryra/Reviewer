/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeAsync;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackReviewsSource;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeSourceCallback extends ReviewTreeAsync
        implements CallbackReviewsSource {
    public ReviewTreeSourceCallback(ReviewNode initial) {
        super(initial);
    }

    @Override
    public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
        if (review != null && !message.isError()) updateNode(review);
    }
}
