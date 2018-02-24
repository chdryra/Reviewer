/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;


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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.Styles;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ReviewViewLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 06/08/2017
 * Email: rizwan.choudrey@gmail.com
 */

public class ReviewEditFragmentLayout implements ReviewViewLayout {
    private static final int LAYOUT = R.layout.fragment_review_edit;
    private static final int SUBJECT = R.id.subject_text;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXT_VIEW = R.id.contextual_view;
    private static final int CONTEXT_BUTTON = R.id.wide_button;

    private static final TitleDecorator CONTEXT_DECORATOR = Styles.TitleDecorators.DONE_BUTTON;
    private static final TitleDecorator BANNER_DECORATOR = Styles.TitleDecorators.OPTION_BUTTON;

    private View mView;

    private MenuUi mMenu;
    private SimpleViewUi<?, Bitmap> mCover;
    private SubjectUi<?> mSubject;
    private SimpleViewUi<?, Float> mRatingBar;
    private ViewUi<?, ?> mBannerButton;
    private GridViewUi<?, ?> mDataView;
    private ViewUi<?, ?> mContextual;

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
        mBannerButton = newBannerButtonUi(reviewView);
        mDataView = newDataViewUi(reviewView, calculator);
        mCover = newCoverUi(reviewView);
        mContextual = newContextUi(reviewView);
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
        mBannerButton.update();
        mDataView.update();
        mContextual.update();
        mCover.update();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return false;
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return false;
    }

    @NonNull
    private ViewUi<?, ?> newContextUi(ReviewView<?> reviewView) {
        return new ContextUi(reviewView, mView.findViewById(CONTEXT_VIEW), CONTEXT_BUTTON, CONTEXT_DECORATOR);
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
    private <T extends GvData> GridViewUi<?, ?> newDataViewUi(ReviewView<T> reviewView,
                                                              CellDimensionsCalculator calculator) {
        return new RecyclerViewUi<>(reviewView, (RecyclerView) mView.findViewById(GRID),
                calculator);
    }

    @NonNull
    private BannerButtonUi newBannerButtonUi(ReviewView<?> reviewView) {
        ButtonAction<?> action = reviewView.getActions().getBannerButtonAction();
        int alpha = reviewView.getParams().getBannerButtonParams().getAlpha();
        return new BannerButtonUi((Button) mView.findViewById(BANNER), action, BANNER_DECORATOR, alpha);
    }

    @NonNull
    private SimpleViewUi<?, Float> newRatingUi(ReviewView<?> reviewView) {
        return new RatingBarRvUi(reviewView, (FrameLayout) mView.findViewById(RATING));
    }

    @NonNull
    private SubjectUi<?> newSubjectUi(ReviewView<?> reviewView) {
        return new SubjectEditUi(reviewView, (EditText) mView.findViewById(SUBJECT));
    }
}
