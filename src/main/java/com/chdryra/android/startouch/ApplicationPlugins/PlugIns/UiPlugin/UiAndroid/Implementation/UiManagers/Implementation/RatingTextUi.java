/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingTextUi extends RatingUi<TextView> {
    private final ReviewView<?> mView;
    private float mRating = 0;

    public RatingTextUi(final ReviewView<?> reviewView, final TextView ratingView) {
        super(ratingView);

        mView = reviewView;
        setBackgroundAlpha(reviewView.getParams().getBannerButtonParams().getAlpha());
    }

    @Override
    public void update(Float value) {
        mRating = value;
        getView().setText(formatRating());
    }

    @Override
    Float getViewValue() {
        return mRating;
    }

    private String formatRating() {
        String formatted = RatingFormatter.twoSignificantDigits(mRating) + "*";
        String reviews = "(" + mView.getDataSize().getSize() + ")";

        return formatted + " " + reviews;
    }
}
