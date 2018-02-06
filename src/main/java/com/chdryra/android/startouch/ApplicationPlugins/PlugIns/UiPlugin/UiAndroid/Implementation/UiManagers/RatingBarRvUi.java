/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;



import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RatingBar;

import com.chdryra.android.startouch.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarRvUi extends RatingBarUi {
    private static final int RATING_BAR = R.id.review_rating_bar;

    private FrameLayout mFrame;

    public RatingBarRvUi(final ReviewView<?> reviewView, FrameLayout ratingBarFrame) {
        super((RatingBar) ratingBarFrame.findViewById(RATING_BAR),
                new ReferenceValueGetter<Float>() {
            @Override
            public Float getValue() {
                return reviewView.getRating();
            }
        });

        mFrame = ratingBarFrame;
        initialise(reviewView);
    }

    private void initialise(ReviewView<?> reviewView) {
        ReviewViewParams.RatingBar params = reviewView.getParams().getRatingBarParams();
        if(!params.isVisible()) {
            mFrame.setVisibility(View.GONE);
            return;
        }

        RatingBar ratingBar = getView();
        setBackgroundAlpha(ReviewViewParams.Alpha.TRANSPARENT.getAlpha());
        mFrame.getBackground().setAlpha(params.getAlpha());

        boolean isEditable = params.isEditable();
        ratingBar.setIsIndicator(!isEditable);

        final RatingBarAction<?> action = reviewView.getActions().getRatingBarAction();
        //TODO sort out this warning about calling performClick for onTouchListener.
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                action.onClick(v);
                return false;
            }
        });

        if (isEditable) ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                action.onRatingChanged(ratingBar, rating, fromUser);
            }
        });

        update();
    }
}
