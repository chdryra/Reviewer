package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

import android.app.Activity;
import android.view.View;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RatingBarAction<T extends GvData> extends ReviewViewAction<T> {
    void onClick(View v);

    void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser);

    float getRating();

    @Override
    void onAttachReviewView();

    @Override
    void onUnattachReviewView();

    @Override
    void attachReviewView(ReviewView<T> reviewView);

    @Override
    Activity getActivity();

    @Override
    ReviewViewAdapter<T> getAdapter();

    @Override
    ReviewView<T> getReviewView();
}
