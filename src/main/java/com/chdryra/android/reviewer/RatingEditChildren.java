/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 January, 2015
 */

package com.chdryra.android.reviewer;

import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 26/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingEditChildren extends RatingEdit {
    public RatingEditChildren(ControllerReviewEditable controller) {
        super(controller);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        super.onRatingChanged(ratingBar, rating, fromUser);
        if (fromUser) getController().getReviewNode().setReviewRatingAverage(false);
    }
}
