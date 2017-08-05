/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.BannerButtonUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ContextualUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverRvUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.DataViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarRvUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RecyclerViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SimpleViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectEditUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentReviewView extends Fragment implements ReviewViewContainer {
    private static final int LAYOUT = R.layout.fragment_review_view;
    private static final int SUBJECT = R.id.subject_edit_text;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;
    private static final int COVER = R.id.background_image;
    private static final int CONTEXTUAL_VIEW = R.id.contextual_view;
    private static final int CONTEXTUAL_BUTTON = R.id.contextual_button;

    private MenuUi mMenu;
    private SimpleViewUi<?, Bitmap> mCover;
    private SubjectUi<?> mSubject;
    private SimpleViewUi<?, Float> mRatingBar;
    private ViewUi<?, ?> mBannerButton;
    private DataViewUi<?, ?> mGridView;
    private ViewUi<?, ?> mContextual;

    private ReviewView<?> mReviewView;
    private boolean mIsAttached = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflateLayout(inflater, container);

        if (mReviewView == null) {
            AppInstanceAndroid.getInstance(getActivity()).getUi().returnToFeedScreen();
            return v;
        }

        mSubject = createSubjectUi(v);
        mRatingBar = createRatingUi(v);
        mBannerButton = createBannerButtonUi(v);
        mGridView = newDataViewUi(v);
        mMenu = newMenuUi();
        mCover = newCoverUi(v);
        mContextual = newContextualUi(v);

        return v;
    }

    @NonNull
    private ViewUi<?, ?> newContextualUi(View v) {
        return new ContextualUi((LinearLayout) v.findViewById(CONTEXTUAL_VIEW),
                CONTEXTUAL_BUTTON, getReviewView().getActions().getContextualAction());
    }

    @NonNull
    private CoverUi newCoverUi(View v) {
        return new CoverRvUi(getReviewView(), (ImageView) v.findViewById(COVER), mGridView);
    }

    @NonNull
    private MenuUi newMenuUi() {
        return new MenuUi(getReviewView().getActions().getMenuAction());
    }

    @NonNull
    private DataViewUi<?, ?> newDataViewUi(View v) {
        return new RecyclerViewUi<>(getReviewView(), (RecyclerView) v.findViewById(GRID),
                new CellDimensionsCalculator(getActivity()));
    }

    @NonNull
    private BannerButtonUi createBannerButtonUi(View v) {
        return new BannerButtonUi((Button) v.findViewById(BANNER),
                getReviewView().getActions().getBannerButtonAction());
    }

    @NonNull
    private SimpleViewUi<?, Float> createRatingUi(View v) {
        return new RatingBarRvUi(getReviewView(), (RatingBar) v.findViewById(RATING));
    }

    @NonNull
    private SubjectUi<?> createSubjectUi(View v) {
        return new SubjectEditUi(getReviewView(), (EditText) v.findViewById(SUBJECT));
    }

    private View inflateLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(LAYOUT, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        attachToReviewViewIfNecessary();
    }

    @Override
    public void onResume() {
        super.onResume();
        attachToReviewViewIfNecessary();
        updateUi(true);
    }

    @Override
    public void onStop() {
        detachFromReviewViewIfNecessary();
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged() {
        updateUi(false);
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
    public ReviewView<?> getReviewView() {
        return mReviewView;
    }

    public void setReviewView(ReviewView<?> reviewView) {
        mReviewView = reviewView;
    }

    @Override
    public void detachFromReviewView() {
        mIsAttached = false;
    }

    @Override
    public void setCover(@Nullable DataImage cover) {
        mCover.setViewValue(cover == null ? null : cover.getBitmap());
    }

    private void attachToReviewViewIfNecessary() {
        if (!mIsAttached) {
            mReviewView.attachEnvironment(this, AppInstanceAndroid.getInstance(getActivity()));
            mIsAttached = true;
        }
    }

    private void detachFromReviewViewIfNecessary() {
        if (mIsAttached) {
            mReviewView.detachEnvironment();
            mIsAttached = false;
        }
    }

    private void updateUi(boolean forceSubject) {
        mSubject.update(forceSubject);
        mRatingBar.update();
        mBannerButton.update();
        mGridView.update();
        mContextual.update();
        mCover.update();
    }
}

