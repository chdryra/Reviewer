package com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces;

import android.app.Activity;
import android.view.View;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GridItemAction<T extends GvData> extends ReviewViewAction<T> {
    void onGridItemClick(T item, int position, View v);

    void onGridItemLongClick(T item, int position, View v);

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
