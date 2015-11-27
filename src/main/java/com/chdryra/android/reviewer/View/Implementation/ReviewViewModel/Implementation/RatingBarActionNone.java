package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.RatingBarAction;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements RatingBarAction<T> {
    //public methods
    @Override
    public float getRating() {
        return getAdapter().getRating();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    @Override
    public void onClick(View v) {

    }
}
