/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.BannerButtonUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ContextualUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverRvUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.GridViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarRvUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectEditUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
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

    private SubjectEditUi mSubject;
    private RatingBarRvUi mRatingBar;
    private BannerButtonUi mBannerButton;
    private GridViewUi<?> mGridView;
    private ContextualUi mContextual;
    private MenuUi mMenu;
    private CoverRvUi mCover;

    private ReviewView<?> mReviewView;
    private boolean mIsAttached = false;

    @Override
    public String getSubject() {
        return mSubject.getText();
    }

    @Override
    public float getRating() {
        return mRatingBar.getRating();
    }

    @Override
    public void setRating(float rating) {
        mRatingBar.setRating(rating);
    }

    @Override
    public ReviewView<?> getReviewView() {
        return mReviewView;
    }

    @Override
    public void detachFromReviewView() {
        mIsAttached = false;
    }

    @Override
    public void setCover(@Nullable DataImage cover) {
        mCover.setCover(cover == null ? null : cover.getBitmap());
    }

    @Override
    public void setCellDimension(ReviewViewParams.CellDimension width,
                                 ReviewViewParams.CellDimension height) {
        mGridView.setCellDimension(width, height);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    public void setReviewView(ReviewView<?> reviewView) {
        mReviewView = reviewView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        if (mReviewView == null) {
            AppInstanceAndroid.getInstance(getActivity()).getUi().returnToFeedScreen();
            return v;
        }

        ReviewViewActions<?> actions = mReviewView.getActions();

        mSubject = new SubjectEditUi(mReviewView, (EditText) v.findViewById(SUBJECT));
        mRatingBar = new RatingBarRvUi(mReviewView, (RatingBar) v.findViewById(RATING));
        int colour = mSubject.getTextColour();
            mBannerButton = new BannerButtonUi((Button) v.findViewById(BANNER),
                actions.getBannerButtonAction());
        mBannerButton.setTextColour(colour);
        mGridView = new GridViewUi<>(mReviewView, (GridView) v.findViewById(GRID), new
                FactoryGridCellAdapter(getActivity()), new CellDimensionsCalculator(getActivity()));
        mMenu = new MenuUi(mReviewView.getActions().getMenuAction());
        mCover = new CoverRvUi(mReviewView, (ImageView) v.findViewById(COVER), mGridView);
        mContextual = new ContextualUi((LinearLayout) v.findViewById(CONTEXTUAL_VIEW),
                CONTEXTUAL_BUTTON, actions.getContextualAction(), colour);

        return v;
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

