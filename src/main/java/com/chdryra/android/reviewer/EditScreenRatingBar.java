/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenRatingBar extends ReviewViewAction.RatingBarAction {
    private ReviewView.Editor mEditor;

    @Override
    public void onAttachReviewView() {
        super.onAttachReviewView();
        mEditor = ReviewView.Editor.cast(getReviewView());
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        mEditor.setRating(rating);
        if (fromUser) mEditor.setRatingAverage(false);
    }
}
