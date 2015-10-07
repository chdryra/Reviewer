/*
* Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Author: Rizwan Choudrey
* Date: 24 January, 2015
*/

package com.chdryra.android.reviewer.View.Screens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewView implements GridDataObservable.GridDataObserver, LaunchableUi {
    private ReviewViewPerspective mPerspective;
    private HashMap<String, Fragment> mActionListeners;
    private ArrayList<GridDataObservable.GridDataObserver> mGridObservers;
    private FragmentReviewView mFragment;
    private GvDataList mGridViewData;

    //Constructors
    public ReviewView(ReviewViewPerspective perspective) {
        mPerspective = perspective;
        mGridObservers = new ArrayList<>();
        mActionListeners = new HashMap<>();

        ReviewViewAdapter adapter = mPerspective.getAdapter();
        adapter.registerReviewView(this);
        adapter.registerGridDataObserver(this);
        setGridViewData(adapter.getGridData());
    }

    //public methods
    public ReviewViewAdapter getAdapter() {
        return mPerspective.getAdapter();
    }

    public ReviewViewParams getParams() {
        return mPerspective.getParams();
    }

    public FragmentReviewView getFragment() {
        return mFragment;
    }

    public Activity getActivity() {
        return mFragment.getActivity();
    }

    public String getSubject() {
        return getAdapter().getSubject();
    }

    public float getRating() {
        return getAdapter().getRating();
    }

    public GvDataList getGridData() {
        return getAdapter().getGridData();
    }

    public GvDataList getGridViewData() {
        return mGridViewData != null ? mGridViewData : getGridData();
    }

    public void setGridViewData(GvDataList dataToShow) {
        mGridViewData = dataToShow;
        updateView();
    }

    public ReviewViewAction.SubjectAction getSubjectViewAction() {
        return mPerspective.getActions().getSubjectAction();
    }

    public ReviewViewAction.RatingBarAction getRatingBarAction() {
        return mPerspective.getActions().getRatingBarAction();
    }

    public ReviewViewAction.BannerButtonAction getBannerButtonAction() {
        return mPerspective.getActions().getBannerButtonAction();
    }

    public ReviewViewAction.GridItemAction getGridItemAction() {
        return mPerspective.getActions().getGridItemAction();
    }

    public ReviewViewAction.MenuAction getMenuAction() {
        return mPerspective.getActions().getMenuAction();
    }

    public boolean isEditable() {
        return false;
    }

    public String getFragmentSubject() {
        return mFragment.getSubject();
    }

    public float getFragmentRating() {
        return mFragment.getRating();
    }

    public void attachFragment(FragmentReviewView parent) {
        if (mFragment != null) throw new RuntimeException("There is a Fragment already attached");
        mFragment = parent;
        mPerspective.getActions().attachReviewView(this);
        registerGridDataObserver(mFragment);
    }

    public void resetGridViewData() {
        mGridViewData = null;
        updateView();
    }

    public void updateCover() {
        if (getParams().manageCover()) {
            GvImageList images = getAdapter().getCovers();
            GvImageList covers = images.getCovers();
            GvImageList.GvImage cover = null;
            if (covers.size() > 0) {
                cover = covers.getRandomCover();
            } else if (images.size() > 0) {
                cover = images.getItem(0);
                cover.setIsCover(true);
            }

            mFragment.setCover(cover);
        }
    }

    public void updateView() {
        notifyObservers();
    }

    public void attachActionListeners() {
        FragmentManager manager = mFragment.getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        for (Map.Entry<String, Fragment> entry : mActionListeners.entrySet()) {
            ft.add(entry.getValue(), entry.getKey());
        }
        ft.commit();
        manager.executePendingTransactions();
    }

    public void registerActionListener(Fragment listener, String tag) {
        if (!mActionListeners.containsKey(tag)) mActionListeners.put(tag, listener);
    }

    public void registerGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (!mGridObservers.contains(observer)) mGridObservers.add(observer);
    }

    public void unregisterGridDataObserver(GridDataObservable.GridDataObserver observer) {
        if (mGridObservers.contains(observer)) mGridObservers.remove(observer);
    }

    public void notifyObservers() {
        for (GridDataObservable.GridDataObserver observer : mGridObservers) {
            observer.onGridDataChanged();
        }
    }

    public View modifyIfNeccesary(View v, LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        ReviewViewPerspective.ReviewViewModifier modifier = mPerspective.getModifier();
        if (modifier != null) {
            return modifier.modify(mFragment, v, inflater, container, savedInstanceState);
        } else {
            return v;
        }
    }

    //Overridden
    @Override
    public void onGridDataChanged() {
        resetGridViewData();
        notifyObservers();
    }

    @Override
    public String getLaunchTag() {
        return getAdapter().getSubject();
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }
}