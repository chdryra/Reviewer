/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;

import com.chdryra.android.corelibrary.FileUtils.FileIncrementor;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Actions.ButtonAction;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryFileIncrementor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditorDefault<GC extends GvDataList<? extends GvDataParcelable>> extends
        ReviewViewDefault<GC>
        implements ReviewEditor<GC>, ButtonAction.ClickListener {
    private final ReviewBuilderAdapter<GC> mAdapter;
    private final UiLauncher mLauncher;
    private final FactoryReviewDataEditor mEditorFactory;
    private final BuildScreenEditMode<GC> mBannerButton;
    private final BuildScreenDataEdit<GC> mGridItem;
    private final ArrayList<ModeListener> mModeListeners;
    private final FactoryFileIncrementor mIncrementorFactory;
    private final FactoryImageChooser mImageChooserFactory;

    private String mCurrentSubject;
    private EditMode mEditMode = EditMode.QUICK;
    private FileIncrementor mIncrementor;

    public ReviewEditorDefault(ReviewBuilderAdapter<GC> adapter,
                               ReviewViewActions<GC> actions,
                               ReviewViewParams params,
                               UiLauncher launcher,
                               FactoryReviewDataEditor editorFactory,
                               FactoryFileIncrementor incrementorFactory,
                               FactoryImageChooser imageChooserFactory) {
        super(new ReviewViewPerspective<>(adapter, actions, params));
        mAdapter = adapter;
        mLauncher = launcher;
        mEditorFactory = editorFactory;
        mIncrementorFactory = incrementorFactory;
        mImageChooserFactory = imageChooserFactory;

        mBannerButton = (BuildScreenEditMode<GC>) actions.getBannerButtonAction();
        mGridItem = (BuildScreenDataEdit<GC>) actions.getGridItemAction();

        mModeListeners = new ArrayList<>();

        mCurrentSubject = mAdapter.getSubject();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        String subject = mAdapter.getSubject();
        if (!mCurrentSubject.equals(subject)) {
            mCurrentSubject = subject;
            newIncrementor();
        }
    }

    @Override
    public ReviewBuilderAdapter<GC> getAdapter() {
        return mAdapter;
    }

    @Override
    public void setSubject() {
        mAdapter.setSubject(getContainerSubject(), true);
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mAdapter.setRatingIsAverage(isAverage);
        if (isAverage) setRating(mAdapter.getRating(), false);
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        if (fromUser) {
            mAdapter.setRatingIsAverage(false);
            mAdapter.setRating(rating);
        } else if (getContainer() != null) {
            getContainer().setRating(rating);
        }
    }

    @Override
    public void attachEnvironment(ReviewViewContainer container, ApplicationInstance app) {
        super.attachEnvironment(container, app);
        mBannerButton.registerListener(this);
        setView();
    }

    @Override
    public void detachEnvironment() {
        super.detachEnvironment();
        mBannerButton.unregisterListener(this);
    }

    @Override
    public GvImage getCover() {
        return mAdapter.getCover();
    }

    @Override
    public void setCover(GvImage image) {
        mAdapter.setCover(image);
    }

    @Override
    public ImageChooser newImageChooser() {
        if (mIncrementor == null) newIncrementor();
        return mImageChooserFactory.newImageChooser(mIncrementor);
    }

    @Override
    public <T extends GvDataParcelable> ReviewDataEditor<T> newDataEditor(GvDataType<T> dataType) {
        DataBuilderAdapter<T> adapter = mAdapter.getDataBuilderAdapter(dataType);
        return mEditorFactory.newEditor(adapter, mLauncher, newImageChooser());
    }

    @Override
    public String getSubject() {
        return mAdapter.getSubject();
    }

    @Override
    public float getRating() {
        return mAdapter.getRating();
    }

    @Override
    public ReadyToBuildResult isReviewBuildable() {
        setSubject();
        if (mAdapter.getSubject() == null || mAdapter.getSubject().length() == 0) {
            return ReadyToBuildResult.NoSubject;
        } else if (mAdapter.getDataBuilderAdapter(GvTag.TYPE).getGridData().size() == 0) {
            return ReadyToBuildResult.NoTags;
        } else {
            return ReadyToBuildResult.YES;
        }
    }

    @Override
    public Review buildReview() {
        return mAdapter.buildReview();
    }

    @Override
    public ReviewNode buildPreview() {
        return mAdapter.buildPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGridItem.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void registerModeListener(ModeListener listener) {
        if (!mModeListeners.contains(listener)) mModeListeners.add(listener);
    }

    @Override
    public void unregisterModeListener(ModeListener listener) {
        if (mModeListeners.contains(listener)) mModeListeners.remove(listener);
    }

    @Override
    public void onButtonClick() {
        setView();
        notifyModeListeners();
    }

    @Override
    public EditMode getEditMode() {
        return mEditMode;
    }

    private void notifyModeListeners() {
        for (ModeListener listener : mModeListeners) {
            listener.onEditMode(mEditMode);
        }
    }

    private void newIncrementor() {
        mIncrementor = mIncrementorFactory.newJpgFileIncrementor(mAdapter.getSubject());
    }

    private void setView() {
        mEditMode = mBannerButton.getEditMode();
        mAdapter.setView(mEditMode);
        mGridItem.setView(mEditMode);
    }
}
