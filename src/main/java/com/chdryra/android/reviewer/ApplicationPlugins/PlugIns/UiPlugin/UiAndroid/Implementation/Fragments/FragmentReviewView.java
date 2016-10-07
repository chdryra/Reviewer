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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.BannerButtonUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ContextualUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.GridViewUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGridCellAdapter;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentReviewView extends Fragment implements ReviewViewContainer {
    private static final int LAYOUT = R.layout.fragment_review_view;
    private static final int MAIN_VIEW = R.id.main_view;
    private static final int SUBJECT = R.id.subject_edit_text;
    private static final int RATING = R.id.review_rating;
    private static final int BANNER = R.id.banner_button;
    private static final int GRID = R.id.gridview_data;

    private static final int CONTEXTUAL_VIEW = R.id.contextual_view;
    private static final int CONTEXTUAL_BUTTON = R.id.contextual_button;

    private SubjectUi mSubject;
    private RatingBarUi mRatingBar;
    private BannerButtonUi mBannerButton;
    private GridViewUi<?> mGridView;
    private MenuUi mMenu;
    private CoverUi mCover;
    private ContextualUi mContextual;

    private ReviewView<?> mReviewView;

    private boolean mIsAttached = false;

    @Override
    public String getSubject() {
        return mSubject.getSubject();
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
    public void setCover(@Nullable DataImage cover) {
        mCover.setCover(cover);
    }

    @Override
    public void updateContextButton() {
        mContextual.update();
    }

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

        if (mReviewView == null) extractReviewView();
        if (mReviewView == null) throw new IllegalStateException("ReviewView cannot be null!");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        View v = inflater.inflate(LAYOUT, container, false);

        LinearLayout mainView = (LinearLayout) v.findViewById(MAIN_VIEW);
        mSubject = new SubjectUi(mReviewView, (EditText) v.findViewById(SUBJECT));
        mRatingBar = new RatingBarUi(mReviewView, (RatingBar) v.findViewById(RATING));
        int colour = mSubject.getTextColour();
        mBannerButton = new BannerButtonUi(mReviewView, (Button) v.findViewById(BANNER), colour);
        mGridView = new GridViewUi<>(mReviewView, (GridView) v.findViewById(GRID), new
                FactoryGridCellAdapter(getActivity()), displayMetrics);
        mMenu = new MenuUi(mReviewView);
        mCover = new CoverUi(mReviewView, mainView, mGridView, getActivity());
        mContextual = new ContextualUi(mReviewView,
                (LinearLayout) v.findViewById(CONTEXTUAL_VIEW), CONTEXTUAL_BUTTON, colour);

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

    private void extractReviewView() {
        ActivityReviewView activity;
        try {
            activity = (ActivityReviewView) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity must be an ActivityReviewView", e);
        }

        mReviewView = activity.getReviewView();
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

    private void updateUi(boolean includeCover) {
        mSubject.update();
        mRatingBar.update();
        mBannerButton.update();
        mGridView.update();
        mContextual.update();
        if(includeCover) mCover.update();
    }
}

