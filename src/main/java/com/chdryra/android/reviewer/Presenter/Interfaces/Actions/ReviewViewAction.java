package com.chdryra.android.reviewer.Presenter.Interfaces.Actions;

import android.app.Activity;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;

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
