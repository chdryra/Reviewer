/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
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
    private ReviewViewContainer mContainer;
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
    public ReviewViewContainer getContainer() {
        if(mContainer == null) throw new IllegalStateException("Cannot call before Container attached");
        return mContainer;
    }

    @Override
    public CurrentScreen getScreen() {
        return getContainer().getApp().getCurrentScreen();
    }

    @Override
    public ReviewViewParams getParams() {
        return mPerspective.getParams();
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
        if(mContainer != null) mContainer.onDataChanged();
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
    public String getContainerSubject() {
        return mContainer.getSubject();
    }

    @Override
    public float getContainerRating() {
        return mContainer.getRating();
    }

    @Override
    public void attachContainer(ReviewViewContainer container) {
        if (mContainer != null) throw new RuntimeException("There is a Fragment already attached");
        mContainer = container;
        mPerspective.getActions().attachReviewView(this);
        registerDataObserver(mContainer);
    }

    @Override
    public void detachContainer(ReviewViewContainer container) {
        unregisterDataObserver(mContainer);
        mContainer = null;
    }

    @Override
    public void updateCover() {
        if (getParams().manageCover()) {
            GvImageList covers = getAdapter().getCovers();
            mContainer.setCover(covers.size() > 0 ? covers.getRandomCover() : null);
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

    protected ReviewViewContainer getParent() {
        return mContainer;
    }
}