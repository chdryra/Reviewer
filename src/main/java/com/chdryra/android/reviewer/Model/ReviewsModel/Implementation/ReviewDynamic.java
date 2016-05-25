/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewDynamic implements Review {
    private ArrayList<ReviewObserver> mObservers;

    public ReviewDynamic() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void registerObserver(ReviewObserver observer) {
        if(!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewObserver observer) {
        if(mObservers.contains(observer)) mObservers.remove(observer);
    }

    protected void notifyReviewObservers() {
        for (ReviewObserver observer : mObservers) {
            observer.onReviewChanged();
        }
    }

    @Override
    public boolean isCacheable() {
        return false;
    }
}
