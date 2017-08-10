/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.widget.TextView;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingTextUi extends RatingUi<TextView> {
    private ReviewView<?> mReviewView;
    private TextView mReviewsNum;

    public RatingTextUi(final ReviewView<?> reviewView, final TextView ratingView, final TextView reviewsNum) {
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
                        String text = RatingFormatter.twoSignificantDigits(reviewView.getRating()
                        ) + "*";
                        ratingView.setText(text);
                    }
                });

        mReviewView = reviewView;
        mReviewsNum = reviewsNum;
    }

    @Override
    public void update() {
        super.update();
        mReviewsNum.setText("(" + mReviewView.getGridData().size() + ")");
    }
}
