/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiTypeLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewDefault<T extends GvData> extends DataObservableDefault implements
        ReviewView<T> {
    private static final String TAG = TagKeyGenerator.getTag(ReviewViewDefault.class);

    private ReviewViewPerspective<T> mPerspective;

    private ReviewViewContainer mContainer;
    private ApplicationInstance mApp;
    private boolean mIsAttached = false;

    public ReviewViewDefault(ReviewViewPerspective<T> perspective) {
        mPerspective = perspective;
    }

    protected ReviewViewContainer getContainer() {
        return mContainer;
    }

    protected void detachPerspective() {
        mPerspective.detachFromActions();
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
    public void onDataChanged() {
        notifyDataObservers();
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
    public ReviewViewActions<T> getActions() {
        return mPerspective.getActions();
    }

    @Override
    public String getContainerSubject() {
        return mContainer != null ? mContainer.getSubject() : "";
    }

    @Override
    public float getContainerRating() {
        return mContainer != null ? mContainer.getRating() : 0f;
    }

    @Override
    public void attachEnvironment(ReviewViewContainer container, ApplicationInstance app) {
        if (mIsAttached) detachEnvironment();

        mContainer = container;
        mApp = app;
        registerObserver(mContainer);
        attachPerspective();
        mIsAttached = true;
        notifyDataObservers();
    }

    @Override
    public void detachEnvironment() {
        detachPerspective();
        unregisterObserver(mContainer);
        mContainer = null;
        mIsAttached = false;
    }

    @Override
    public String getLaunchTag() {
        return getAdapter().getStamp() + " " + TAG;
    }

    @Override
    public void launch(UiTypeLauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mPerspective.getActions().onOptionSelected(requestCode, option);
    }

    @Override
    public boolean onOptionsCancelled(int requestCode) {
        return mPerspective.getActions().onOptionsCancelled(requestCode);
    }

    private void attachPerspective() {
        mPerspective.attachToActions(this);
        mPerspective.attachToAdapter(this);
    }
}