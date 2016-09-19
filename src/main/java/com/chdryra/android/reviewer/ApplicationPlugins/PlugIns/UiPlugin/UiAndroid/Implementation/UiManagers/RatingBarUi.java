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
public class RatingBarUi {
    private final ReviewView<?> mReviewView;
    private final RatingBar mView;

    public RatingBarUi(ReviewView<?> reviewView, RatingBar view) {
        mReviewView = reviewView;
        mView = view;
        initialise();
    }

    public float getRating() {
        return mView.getRating();
    }

    public void setRating(float rating) {
        mView.setRating(rating);
    }

    public void update() {
        setRating(mReviewView.getRating());
    }

    private void initialise() {
        boolean isEditable = mReviewView.getParams().getRatingBarParams().isEditable();
        boolean isVisible = mReviewView.getParams().getRatingBarParams().isVisible();

        if(!isVisible) {
            mView.setVisibility(View.GONE);
            return;
        }

        mView.setIsIndicator(!isEditable);
        RatingBarAction<?> action = mReviewView.getActions().getRatingBarAction();
        mView.setOnTouchListener(newRatingBarTouchListener(action));
        if (isEditable) {
            mView.setOnRatingBarChangeListener(newRatingBarChangeListener(action));
        }

        update();
    }

    @NonNull
    private View.OnTouchListener newRatingBarTouchListener(final RatingBarAction<?> action) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                action.onClick(v);
                return false;
            }
        };
    }

    @NonNull
    private RatingBar.OnRatingBarChangeListener newRatingBarChangeListener(final RatingBarAction<?> action) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                action.onRatingChanged(ratingBar, rating, fromUser);
            }
        };
    }
}
