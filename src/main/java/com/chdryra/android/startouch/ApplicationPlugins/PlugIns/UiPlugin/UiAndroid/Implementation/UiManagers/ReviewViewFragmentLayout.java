/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewViewFragmentLayout implements ReviewViewLayout {
    private static final int LAYOUT = R.layout.fragment_review_view;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.rating_button;
    private static final int BANNER = R.id.sort_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXT_VIEW = R.id.view_selector_layout;
    private static final int CONTEXT_BUTTON = R.id.view_button;

    private View mView;

    private MenuUi mMenu;
    private SimpleViewUi<?, Bitmap> mCover;
    private SubjectUi<?> mSubject;
    private SimpleViewUi<?, Float> mRatingBar;
    private ViewUi<?, ?> mSortButton;
    private RecyclerViewUi<?> mDataView;
    private ViewUi<?, ?> mViewSelector;

    @Override
    public View inflateLayout(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(LAYOUT, container, false);
        return mView;
    }

    @Override
    public <T extends GvData> void attachReviewView(ReviewView<T> reviewView,
                                                    CellDimensionsCalculator calculator) {
        mMenu = newMenuUi(reviewView);
        mSubject = newSubjectUi(reviewView);
        mRatingBar = newRatingUi(reviewView);
        mSortButton = newBannerButtonUi(reviewView);
        mDataView = newDataViewUi(reviewView, calculator);
        mCover = newCoverUi(reviewView);
        mViewSelector = newContextUi(reviewView);
    }

    @Override
    public void inflateMenu(Menu menu, MenuInflater inflater) {
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onMenuItemSelected(MenuItem item) {
        return mMenu.onItemSelected(item);
    }

    @Override
    public String getSubject() {
        return mSubject.getViewValue();
    }

    @Override
    public float getRating() {
        return mRatingBar.getViewValue();
    }

    @Override
    public void setRating(float rating) {
        mRatingBar.setViewValue(rating);
    }

    @Override
    public void setCover(@Nullable Bitmap cover) {
        mCover.setViewValue(cover);
    }

    @Override
    public void update(boolean forceSubject) {
        mSubject.update(forceSubject);
        mRatingBar.update();
        mSortButton.update();
        mDataView.update();
        mViewSelector.update();
        mCover.update();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mDataView.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mDataView.onOptionsCancelled(requestCode);
    }

    @NonNull
    private ViewUi<?, ?> newContextUi(ReviewView<?> reviewView) {
        return new ContextUi(reviewView, mView.findViewById(CONTEXT_VIEW), CONTEXT_BUTTON);
    }

    @NonNull
    private CoverUi newCoverUi(ReviewView<?> reviewView) {
        return new CoverRvUi(reviewView, (ImageView) mView.findViewById(COVER), mDataView);
    }

    @NonNull
    private MenuUi newMenuUi(ReviewView<?> reviewView) {
        return new MenuUi(reviewView.getActions().getMenuAction());
    }

    @NonNull
    private <T extends GvData> RecyclerViewUi<T> newDataViewUi(ReviewView<T> reviewView,
                                                               CellDimensionsCalculator
                                                                       calculator) {
        return new RecyclerViewUi<>(reviewView, (RecyclerView) mView.findViewById(GRID),
                calculator);
    }

    @NonNull
    private BannerButtonUi newBannerButtonUi(ReviewView<?> reviewView) {
        return new BannerButtonUi(reviewView, (Button) mView.findViewById(BANNER));
    }

    @NonNull
    private SimpleViewUi<?, Float> newRatingUi(ReviewView<?> reviewView) {
        return new RatingTextUi(reviewView, (TextView) mView.findViewById(RATING));
    }

    @NonNull
    private SubjectUi<?> newSubjectUi(final ReviewView<?> reviewView) {
        return new SubjectViewUi(reviewView, (TextView) mView.findViewById(SUBJECT));
    }
}
