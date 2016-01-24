/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewViewPacker;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityReviewView;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewDefault<T extends GvData> implements ReviewView<T> {
    private static final String TAG = "ReviewViewDefault";
    private ReviewViewPerspective<T> mPerspective;
    private ArrayList<GridDataObservable.GridDataObserver> mGridObservers;
    private FragmentReviewView mFragment;
    private GvDataList<T> mGridViewData;

    //Constructors
    public ReviewViewDefault(ReviewViewPerspective<T> perspective) {
        mPerspective = perspective;
        mGridObservers = new ArrayList<>();

        ReviewViewAdapter<T> adapter = mPerspective.getAdapter();
        adapter.attachReviewView(this);
        adapter.registerGridDataObserver(this);
        mGridViewData = adapter.getGridData();
    }

    //public methods
    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return mPerspective.getAdapter();
    }

    @Override
    public ReviewViewParams getParams() {
        return mPerspective.getParams();
    }

    @Override
    public Activity getActivity() {
        return mFragment.getActivity();
    }

    @Override
    public String getSubject() {
        return getAdapter().getSubject();
    }

    @Override
    public float getRating() {
        return getAdapter().getRating();
    }

    @Override
    public GvDataList<T> getGridData() {
        return getAdapter().getGridData();
    }

    @Override
    public GvDataList<T> getGridViewData() {
        return mGridViewData != null ? mGridViewData : getGridData();
    }

    @Override
    public void setGridViewData(GvDataList<T> dataToShow) {
        mGridViewData = dataToShow;
        if(mFragment != null) mFragment.onGridDataChanged();
    }

    @Override
    public ReviewViewActions<T> getActions() {
        return mPerspective.getActions();
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public String getFragmentSubject() {
        return mFragment.getSubject();
    }

    @Override
    public float getFragmentRating() {
        return mFragment.getRating();
    }

    @Override
    public void attachFragment(FragmentReviewView parent) {
        if (mFragment != null) throw new RuntimeException("There is a Fragment already attached");
        mFragment = parent;
        mPerspective.getActions().attachReviewView(this);
        registerGridDataObserver(mFragment);
    }

    @Override
    public void resetGridViewData() {
        mGridViewData = null;
        notifyObservers();
    }

    @Override
    public void updateCover() {
        if (getParams().manageCover()) {
            GvImageList covers = getAdapter().getCovers();
            if (covers.size() > 0) {
                mFragment.setCover(covers.getRandomCover());
            }
        }
    }

    @Override
    public void registerGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (!mGridObservers.contains(observer)) mGridObservers.add(observer);
    }

    @Override
    public void unregisterGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (mGridObservers.contains(observer)) mGridObservers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (GridDataObservable.GridDataObserver observer : mGridObservers) {
            observer.onGridDataChanged();
        }
    }

    @Override
    public View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        ReviewViewPerspective.ReviewViewModifier modifier = mPerspective.getModifier();
        if (modifier != null) {
            return modifier.modify(mFragment, v, inflater, container, savedInstanceState);
        } else {
            return v;
        }
    }

    @Override
    public void onGridDataChanged() {
        resetGridViewData();
        notifyObservers();
    }

    @Override
    public String getLaunchTag() {
        return getSubject() + " " + TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        Activity commissioner = launcher.getCommissioner();
        Intent i = new Intent(commissioner, ActivityReviewView.class);
        ReviewViewPacker.packView(commissioner, this, i);
        commissioner.startActivityForResult(i, launcher.getRequestCode());
    }
}