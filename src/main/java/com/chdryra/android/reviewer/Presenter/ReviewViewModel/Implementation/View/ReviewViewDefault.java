/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments.FragmentReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
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
    private ArrayList<DataObservable.DataObserver> mObservers;
    private FragmentReviewView mFragment;
    private GvDataList<T> mGridViewData;

    public ReviewViewDefault(ReviewViewPerspective<T> perspective) {
        mPerspective = perspective;
        mObservers = new ArrayList<>();

        ReviewViewAdapter<T> adapter = mPerspective.getAdapter();
        adapter.attachReviewView(this);
        adapter.registerDataObserver(this);
        mGridViewData = adapter.getGridData();
    }

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
        if(mGridViewData == null) mGridViewData = getGridData();
        return mGridViewData;
    }

    @Override
    public void setGridViewData(GvDataList<T> dataToShow) {
        mGridViewData = dataToShow;
        if(mFragment != null) mFragment.onDataChanged();
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
        registerDataObserver(mFragment);
    }

    @Override
    public void detachFragment(FragmentReviewView parent) {
        unregisterDataObserver(mFragment);
        mFragment = null;
    }

    @Override
    public void updateCover() {
        if (getParams().manageCover()) {
            GvImageList covers = getAdapter().getCovers();
            mFragment.setCover(covers.size() > 0 ? covers.getRandomCover() : null);
        }
    }

    @Override
    public void registerDataObserver(DataObservable.DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterDataObserver(DataObservable.DataObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void notifyDataObservers() {
        for (DataObservable.DataObserver observer : mObservers) {
            observer.onDataChanged();
        }
    }

    @Override
    public View modifyIfNeccessary(View v, LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        ReviewViewModifier modifier = mPerspective.getModifier();
        if (modifier != null) {
            return modifier.modify(mFragment, v, inflater, container, savedInstanceState);
        } else {
            return v;
        }
    }

    @Override
    public void onDataChanged() {
        mGridViewData = null;
        notifyDataObservers();
    }

    @Override
    public String getLaunchTag() {
        return getSubject() + " " + TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    protected FragmentReviewView getParent() {
        return mFragment;
    }
}