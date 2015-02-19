/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 6 February, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 06/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingEditBuildReview extends RatingEdit {
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        super.onRatingChanged(ratingBar, rating, fromUser);
        if (fromUser) ((ReviewBuilder) getAdapter()).setRatingIsAverage(false);
        ((ReviewBuilder) getAdapter()).setRating(rating);
    }
}
