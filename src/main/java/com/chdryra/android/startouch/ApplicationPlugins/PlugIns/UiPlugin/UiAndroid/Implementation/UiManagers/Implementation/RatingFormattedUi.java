/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.UiManagers.Implementation;


import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingFormattedUi extends SimpleViewUi<RatingBar, DataRating> {
    private ReviewId mReviewId;

    public RatingFormattedUi(RatingBar view, @Nullable final Command onTouch) {
        super(view);
        if (onTouch != null) {
            getView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) onTouch.execute();
                    return true;
                }
            });
        }
    }

    @Override
    DataRating getValue() {
        ReviewId reviewId = mReviewId != null ? mReviewId : new DatumReviewId();
        return new DatumRating(reviewId, getView().getRating(), 1);
    }

    @Override
    public void update(DataRating value) {
        mReviewId = value.getReviewId();
        getView().setRating(value.getRating());
    }
}
