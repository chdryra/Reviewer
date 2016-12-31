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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewDefault<T extends GvData> extends DataObservableDefault implements
        ReviewView<T> {
    private static final String TAG = TagKeyGenerator.getTag(ReviewViewDefault.class);

    private final ReviewViewPerspective<T> mPerspective;

    private ReviewViewContainer mContainer;
    private ApplicationInstance mApp;
    private GvDataList<T> mGridViewData;

    public ReviewViewDefault(ReviewViewPerspective<T> perspective) {
        mPerspective = perspective;
    }

    protected void setCellDimension(ReviewViewParams.CellDimension width, ReviewViewParams
            .CellDimension height) {
        mContainer.setCellDimension(width, height);
    }

    protected void attachToAdapter() {
        mPerspective.attachToAdapter(this);
        mGridViewData = mPerspective.getAdapter().getGridData();
        notifyDataObservers();
    }

    protected void detachFromAdapter() {
        mPerspective.detachFromAdapter();
    }

    @Override
    public ReviewViewAdapter<T> getAdapter() {
        return mPerspective.getAdapter();
    }

    @Override
    public ApplicationInstance getApp() {
        if (mApp == null) throw new IllegalStateException("Environment not attached");

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
    public GvDataList<T> getAdapterData() {
        return getAdapter().getGridData();
    }

    @Override
    public GvDataList<T> getGridData() {
        if (mGridViewData == null) mGridViewData = getAdapterData();
        return mGridViewData;
    }

    protected void setGridViewData(GvDataList<T> dataToShow) {
        mGridViewData = dataToShow;
        if (mContainer != null) mContainer.onDataChanged();
    }

    protected ReviewViewContainer getContainer() {
        return mContainer;
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
        if (mContainer != null) {
            if(mContainer != container) {
                unregisterObserver(mContainer);
                mContainer.detachFromReviewView();
                mContainer = container;
                registerObserver(mContainer);
                notifyDataObservers();
            }
            return;
        }

        mContainer = container;
        mApp = app;
        registerObserver(mContainer);
        mPerspective.attachToActions(this);
        attachToAdapter();
    }

    @Override
    public void detachEnvironment() {
        mPerspective.detachFromActions();
        detachFromAdapter();
        unregisterObserver(mContainer);
        mContainer = null;
        nullifyGridData();
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
    public void onDataChanged() {
        nullifyGridData();
        notifyDataObservers();
    }

    protected void nullifyGridData() {
        mGridViewData = null;
    }

    @Override
    public String getLaunchTag() {
        return getSubject() + " " + TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mPerspective.getActions().onOptionSelected(requestCode, option);
    }
}