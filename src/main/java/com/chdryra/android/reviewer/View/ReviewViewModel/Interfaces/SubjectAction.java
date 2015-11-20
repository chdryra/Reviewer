package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

import android.app.Activity;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface SubjectAction<T extends GvData> extends ReviewViewAction<T> {
    void onKeyboardDone(CharSequence s);

    String getSubject();

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
