/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation;

import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarActionNone<T extends GvData> extends ReviewViewActionBasic<T>
        implements RatingBarAction<T> {
    //public methods
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

    }

    @Override
    public void onClick(View v) {

    }
}
