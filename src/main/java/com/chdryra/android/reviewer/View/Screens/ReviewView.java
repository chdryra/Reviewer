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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
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
    private HashMap<String, Fragment>     mActionListeners;
    private ArrayList<GridDataObservable.GridDataObserver> mGridObservers;
    private FragmentReviewView mParent;
    private GvDataList mGridViewData;

    public ReviewView(ReviewViewPerspective perspective) {
        mPerspective = perspective;
        mGridObservers = new ArrayList<>();
        mActionListeners = new HashMap<>();
        configure();
    }

    private void configure() {
        ReviewViewAdapter adapter = mPerspective.getAdapter();
        adapter.registerReviewView(this);
        adapter.registerGridDataObserver(this);
        setGridViewData(adapter.getGridData());
    }

    public ReviewViewAdapter getAdapter() {
        return mPerspective.getAdapter();
    }

    public void attachFragment(FragmentReviewView parent) {
        if (mParent != null) throw new RuntimeException("There is a Fragment already attached");
        mParent = parent;
        mPerspective.getActions().attachReviewView(this);
        registerGridDataObserver(mParent);
    }

    public ReviewViewParams getParams() {
        return mPerspective.getParams();
    }

    public FragmentReviewView getParent() {
        return mParent;
    }

    public Activity getActivity() {
        return mParent.getActivity();
    }

    public GvDataList getGridData() {
        return getAdapter().getGridData();
    }

    public GvDataList getGridViewData() {
        return mGridViewData != null ? mGridViewData : getGridData();
    }

    public void setGridViewData(GvDataList dataToShow) {
        mGridViewData = dataToShow;
        updateParent();
    }

    public void resetGridViewData() {
        mGridViewData = null;
        updateParent();
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

            mParent.setCover(cover);
        }
    }

    public void updateUi() {
        mParent.updateUi();
    }

    public void attachActionListeners() {
        FragmentManager manager = mParent.getFragmentManager();
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

    public String getSubject() {
        return mParent.getSubject();
    }

    public float getRating() {
        return mParent.getRating();
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
            return modifier.modify(mParent, v, inflater, container, savedInstanceState);
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
        return getAdapter().getSubject();
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    private void updateParent() {
        if (mParent != null) mParent.updateGridData();
    }

    public static class Editor extends ReviewView {
        private FragmentReviewView mParent;
        private boolean mRatingIsAverage = false;

        public Editor(ReviewBuilderAdapter.DataBuilderAdapter builder,
                      ReviewViewParams params,
                      ReviewViewActionCollection actions) {
            super(new ReviewViewPerspective(builder, params, actions));
            mRatingIsAverage = builder.getParentBuilder().isRatingAverage();
        }

        public Editor(ReviewBuilderAdapter builder, ReviewViewParams params,
                      ReviewViewActionCollection actions,
                      ReviewViewPerspective.ReviewViewModifier modifier) {
            super(new ReviewViewPerspective(builder, params, actions, modifier));
            mRatingIsAverage = builder.isRatingAverage();
        }

        public static Editor cast(ReviewView view) {
            Editor editor;
            try {
                editor = (ReviewView.Editor) view;
            } catch (ClassCastException e) {
                throw new ClassCastException("ReviewView must be an Editor");
            }
            return editor;
        }

        @Override
        public void attachFragment(FragmentReviewView parent) {
            mParent = parent;
            super.attachFragment(parent);
        }

        @Override
        public boolean isEditable() {
            return true;
        }

        @Override
        public float getRating() {
            return mRatingIsAverage ? getAdapter().getAverageRating() : super.getRating();
        }

        public void setRating(float rating) {
            mParent.setRating(rating);
        }

        public boolean isRatingAverage() {
            return mRatingIsAverage;
        }

        public void setRatingAverage(boolean isAverage) {
            mRatingIsAverage = isAverage;
        }

        public void proposeCover(GvImageList.GvImage image) {
            if (getParams().manageCover()) {
                GvImageList images = getAdapter().getCovers();
                GvImageList covers = images.getCovers();
                if (covers.size() == 1 && images.contains(image)) {
                    covers.getItem(0).setIsCover(false);
                    image.setIsCover(true);
                }
            }
        }

        public void setSubject(String subject) {
            mParent.setSubject(subject);
        }
    }
}