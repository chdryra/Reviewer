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
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation.CellDimensionsCalculator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Interfaces.ReviewViewLayout;
import com.chdryra.android.startouch.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewContainer;

/**
 * Created by: Rizwan Choudrey
 * On: 23/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentReviewView extends Fragment implements ReviewViewContainer, OptionSelectListener{
    private ReviewView<?> mReviewView;
    private ReviewViewLayout mLayout;
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

        mLayout = activity.getReviewLayout();
        View v = mLayout.inflateLayout(inflater, container);
        setReviewView(activity.getReviewView());

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
        mLayout.inflateMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mLayout.onMenuItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged() {
        updateUi(false);
    }

    @Override
    public void setReviewView(ReviewView<?> reviewView) {
        detachFromReviewViewIfNecessary();
        mReviewView = reviewView;
        if(mReviewView != null) {
            mLayout.attachReviewView(mReviewView, new CellDimensionsCalculator(getActivity()));
            attachToReviewViewIfNecessary();
        } else {
            AppInstanceAndroid.getInstance(getActivity()).getUi().returnToFeedScreen();
        }
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
    public ReviewView<?> getReviewView() {
        return mReviewView;
    }

    @Override
    public void detachFromReviewView() {
        mIsAttached = false;
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
        //TODO get rid of the hacky forceSubject thing...
        mLayout.bind();
    }
}

