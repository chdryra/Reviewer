/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewStatic implements Review {
    @Override
    public final void registerObserver(ReviewObserver observer) {
        //Does nothing
    }

    @Override
    public final void unregisterObserver(ReviewObserver observer) {
        //Does nothing
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}
