/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers;


import android.support.annotation.NonNull;
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
public class RatingBarEditUi extends RatingBarUi {

    public RatingBarEditUi(ReviewView<?> reviewView, RatingBar view) {
        super(reviewView, view);
        initialise(reviewView);
    }

    private void initialise(ReviewView<?> reviewView) {
        boolean isEditable = reviewView.getParams().getRatingBarParams().isEditable();
        boolean isVisible = reviewView.getParams().getRatingBarParams().isVisible();

        if(!isVisible) {
            getView().setVisibility(View.GONE);
            return;
        }

        getView().setIsIndicator(!isEditable);
        RatingBarAction<?> action = reviewView.getActions().getRatingBarAction();
        getView().setOnTouchListener(newTouchListener(action));
        if (isEditable) getView().setOnRatingBarChangeListener(newChangeListener(action));

        update();
    }

    @NonNull
    private View.OnTouchListener newTouchListener(final RatingBarAction<?> action) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                action.onClick(v);
                return false;
            }
        };
    }

    @NonNull
    private RatingBar.OnRatingBarChangeListener newChangeListener(final RatingBarAction<?> action) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                action.onRatingChanged(ratingBar, rating, fromUser);
            }
        };
    }
}
