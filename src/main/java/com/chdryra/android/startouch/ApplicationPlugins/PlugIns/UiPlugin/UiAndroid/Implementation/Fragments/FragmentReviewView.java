/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivityReviewView;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Implementation.CellDimensionsCalculator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .UiManagers.Interfaces.ReviewViewContainerLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewContainer;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentReviewView extends Fragment implements ReviewViewContainer,
        OptionSelectListener {
    private ReviewView<?> mReviewView;
    private ReviewViewContainerLayout mLayout;
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

        ActivityReviewView activity;
        try {
            activity = (ActivityReviewView) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity must be ActivityReviewView!", e);
        }

        ReviewViewContainerLayout layout = activity.getContainerLayout();
        View v = layout.inflateLayout(inflater, container);
        bind(layout, activity.getReviewView());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mReviewView != null) {
            attach();
            mLayout.bind();
        } else {
            returnToFeedScreen();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        attach();
    }

    @Override
    public void onStop() {
        detach();
        mLayout.unbind();
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mLayout.inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mLayout.onMenuItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mLayout.onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mLayout.onOptionsCancelled(requestCode);
    }

    @Override
    public String getSubject() {
        return mLayout.getSubject();
    }

    @Override
    public float getRating() {
        return mLayout.getRating();
    }

    @Override
    public void setRating(float rating) {
        mLayout.setRating(rating);
    }

    @Override
    public void detach() {
        if (mIsAttached) {
            mReviewView.detachEnvironment();
            mIsAttached = false;
        }
    }

    private void bind(ReviewViewContainerLayout layout, ReviewView<?> reviewView) {
        mLayout = layout;
        detach();
        mReviewView = reviewView;
        if (mReviewView != null) {
            mLayout.bindToReviewView(mReviewView, new CellDimensionsCalculator(getActivity()));
        } else {
            returnToFeedScreen();
        }
    }

    private void returnToFeedScreen() {
        AppInstanceAndroid.getInstance(getActivity()).getUi().returnToFeedScreen();
    }

    private void attach() {
        if (!mIsAttached) {
            mReviewView.attachEnvironment(this, AppInstanceAndroid.getInstance(getActivity()));
            mIsAttached = true;
        }
    }
}

