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
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryMessage;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSourceCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeSourceCallback extends ReviewTreeAsync
        implements ReviewsSourceCallback {
    public ReviewTreeSourceCallback(ReviewNode initial) {
        super(initial);
    }

    @Override
    public void onMetaReview(@Nullable ReviewNode review, RepositoryMessage error) {
        if (review != null && !error.isError()) updateNode(review);
    }
}
