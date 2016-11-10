/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RatingBarRvUi extends RatingBarUi {
    public RatingBarRvUi(final ReviewView<?> reviewView, RatingBar view) {
        super(view, new ValueGetter<Float>() {
            @Override
            public Float getValue() {
                return reviewView.getRating();
            }
        });

        initialise(reviewView);
    }

    private void initialise(ReviewView<?> reviewView) {
        if(!reviewView.getParams().getRatingBarParams().isVisible()) {
            getView().setVisibility(View.GONE);
            return;
        }

        boolean isEditable = reviewView.getParams().getRatingBarParams().isEditable();
        getView().setIsIndicator(!isEditable);

        final RatingBarAction<?> action = reviewView.getActions().getRatingBarAction();
        getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                action.onClick(v);
                return false;
            }
        });

        if (isEditable) getView().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                action.onRatingChanged(ratingBar, rating, fromUser);
            }
        });

        update();
    }
}
