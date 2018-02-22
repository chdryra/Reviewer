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

    public RatingTextUi(final ReviewView<?> reviewView, final TextView ratingView) {
        super(ratingView, new ReferenceValueGetter<Float>() {
                    @Override
                    public Float getValue() {
                        return reviewView.getRating();
                    }
                }, new ViewValueGetter<Float>() {
                    @Override
                    public Float getValue() {
                        return Float.valueOf(ratingView.getText().toString());
                    }
                },
                new ViewValueSetter<Float>() {
                    @Override
                    public void setValue(Float value) {
                        ratingView.setText(formatRating(reviewView));
                    }
                });

        setBackgroundAlpha(reviewView.getParams().getBannerButtonParams().getAlpha());
    }

    private static String formatRating(ReviewView<?> reviewView) {
        String rating = RatingFormatter.twoSignificantDigits(reviewView.getRating()
        ) + "*";
        String reviews = "(" + reviewView.getGridData().size() + ")";

        return rating + " " + reviews;
    }
}
