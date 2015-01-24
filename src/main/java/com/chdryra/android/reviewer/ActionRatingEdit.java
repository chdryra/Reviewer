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
public class ActionRatingEdit extends ReviewView.RatingBarAction {
    public ActionRatingEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        super(controller, dataType);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ControllerReviewEditable controller = (ControllerReviewEditable) getController();
        controller.setRating(rating);
    }
}
