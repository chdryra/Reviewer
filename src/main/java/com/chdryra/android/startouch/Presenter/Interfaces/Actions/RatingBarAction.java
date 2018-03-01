/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Actions;

import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RatingBarAction<T extends GvData> extends ReviewViewAction<T> {
    void onClick(View v);

    void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser);
}
