package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

import android.app.Activity;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewViewAction<T extends GvData> {
    void onAttachReviewView();

    void onUnattachReviewView();

    void attachReviewView(ReviewView<T> reviewView);

    Activity getActivity();

    ReviewViewAdapter<T> getAdapter();

    ReviewView<T> getReviewView();
}
