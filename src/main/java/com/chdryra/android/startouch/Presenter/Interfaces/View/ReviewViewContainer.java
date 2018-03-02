/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;


/**
 * Created by: Rizwan Choudrey
 * On: 25/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewViewContainer extends DataObservable.DataObserver {
    String getSubject();

    float getRating();

    void setRating(float rating);

    ReviewView<?> getReviewView();

    void detachFromReviewView();

    void setReviewView(ReviewView<?> reviewView);
}
