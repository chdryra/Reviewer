/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 15/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SelectorMostRecent implements ReviewSelector.Selector {
    @Override
    public ReviewReference select(ReviewReference lhs, @Nullable ReviewReference rhs) {
        if(rhs == null) return lhs;

        long lht = lhs.getPublishDate().getTime();
        long rht = rhs.getPublishDate().getTime();
        if(lht > rht) return lhs;
        if(lht < rht) return rhs;

        float lhr = lhs.getRating().getRating();
        float rhr = rhs.getRating().getRating();
        if(lhr > rhr) return lhs;
        if(lhr < rhr) return rhs;

        return lhs;
    }
}
