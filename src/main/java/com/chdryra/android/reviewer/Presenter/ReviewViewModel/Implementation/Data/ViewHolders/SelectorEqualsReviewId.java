/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SelectorEqualsReviewId implements ReviewSelector.Selector {
    private final ReviewId mId;

    public SelectorEqualsReviewId(ReviewId id) {
        mId = id;
    }

    @Override
    public ReviewReference select(ReviewReference lhs, @Nullable ReviewReference rhs) {
        return lhs.getReviewId().equals(mId) ? lhs : rhs;
    }
}
