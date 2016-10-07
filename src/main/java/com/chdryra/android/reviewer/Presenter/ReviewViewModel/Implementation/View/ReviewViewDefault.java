/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewDefault<T extends GvData> implements ReviewView<T> {
    private static final String TAG = TagKeyGenerator.getTag(ReviewViewDefault.class);
    private final ReviewViewPerspective<T> mPerspective;
    private final ArrayList<DataObservable.DataObserver> mObservers;
    private ReviewViewContainer mContainer;
    private ApplicationInstance mApp;
    private GvDataList<T> mGridViewData;
    private boolean mIsAttached = false;

    public ReviewViewDefault(ReviewViewPerspective<T> perspective) {
        mPerspective = perspective;
        mObservers = new ArrayList<>();

        attachToAdapter();
    }

    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return mPerspective.getAdapter();
    }

    @Override
    public ApplicationInstance getApp() {
        if (mApp == null) {
            throw new IllegalStateException("Cannot call before Environment attached");
        }

        return mApp;
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mApp.getUi().getCurrentScreen();
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
        if (mGridViewData == null) mGridViewData = getGridData();
        return mGridViewData;
    }

    @Override
    public void setGridViewData(GvDataList<T> dataToShow) {
        mGridViewData = dataToShow;
        if (mContainer != null) mContainer.onDataChanged();
    }

    @Override
    public ReviewViewActions<T> getActions() {
        return mPerspective.getActions();
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
    public void attachEnvironment(ReviewViewContainer container, ApplicationInstance app) {
        if (mContainer != null) throw new RuntimeException("There is a Fragment already attached");
        mContainer = container;
        mApp = app;
        registerObserver(mContainer);
        mPerspective.attachToActions(this);
        if (!mIsAttached) attachToAdapter();
    }

    @Override
    public void detachEnvironment() {
        unregisterObserver(mContainer);
        mPerspective.detach();
        mContainer = null;
        mGridViewData = null;
        mIsAttached = false;
    }

    @Override
    public void updateCover() {
        if (getParams().manageCover()) {
            getAdapter().getCover(new ReviewViewAdapter.CoverCallback() {
                @Override
                public void onAdapterCover(GvImage cover) {
                    if (mContainer != null) mContainer.setCover(cover);
                }
            });
        }
    }

    @Override
    public void updateContextButton() {
        mContainer.updateContextButton();
    }

    @Override
    public void registerObserver(DataObservable.DataObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DataObservable.DataObserver observer) {
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
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    private void attachToAdapter() {
        mPerspective.attachToAdapter(this);
        mIsAttached = true;
        mGridViewData = mPerspective.getAdapter().getGridData();
    }
}