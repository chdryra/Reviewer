package com.chdryra.android.reviewer.View.Screens;

import android.view.View;
import android.widget.RatingBar;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarAction extends ReviewViewAction {
    //public methods
    public float getRating() {
        return getAdapter().getRating();
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    public void onClick(View v) {

    }
}
