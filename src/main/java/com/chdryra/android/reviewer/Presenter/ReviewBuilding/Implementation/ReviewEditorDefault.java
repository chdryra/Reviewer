/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewContainer;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditorDefault<GC extends GvDataList<? extends GvDataParcelable>> extends ReviewViewDefault<GC>
        implements ReviewEditor<GC>, BannerButtonAction.ClickListener {
    private final ReviewBuilderAdapter<?> mAdapter;
    private final FactoryReviewDataEditor mEditorFactory;
    private final BannerButtonReviewBuild<GC> mBannerButton;
    private final GridItemBuildReview<GC> mGridItem;
    private final ArrayList<BuildListener> mBuildListeners;
    private final ArrayList<ModeListener> mModeListeners;

    private ReviewViewContainer mContainer;
    private EditMode mEditMode = EditMode.QUICK;

    public ReviewEditorDefault(ReviewBuilderAdapter<GC> adapter,
                               ReviewViewActions<GC> actions,
                               ReviewViewParams params,
                               FactoryReviewDataEditor editorFactory) {
        super(new ReviewViewPerspective<>(adapter, actions, params));
        mAdapter = adapter;
        mEditorFactory = editorFactory;
        mBannerButton = (BannerButtonReviewBuild<GC>) actions.getBannerButtonAction();
        mGridItem = (GridItemBuildReview<GC>) actions.getGridItemAction();
        mBuildListeners = new ArrayList<>();
        mModeListeners = new ArrayList<>();
    }

    @Override
    public void setSubject() {
        mAdapter.setSubject(getContainerSubject());
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mAdapter.setRatingIsAverage(isAverage);
        if (isAverage) setRating(mAdapter.getRating(), false);
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        if (fromUser) {
            mAdapter.setRating(rating);
        } else {
            mContainer.setRating(rating);
        }
    }

    @Override
    public void attachEnvironment(ReviewViewContainer container, ApplicationInstance app) {
        super.attachEnvironment(container, app);
        mContainer = container;
        mBannerButton.registerListener(this);
        setView();
    }

    @Override
    public void detachEnvironment() {
        super.detachEnvironment();
        mContainer = null;
        mBannerButton.unregisterListener(this);
    }

    @Override
    public GvImage getCover() {
        return mAdapter.getCover();
    }

    @Override
    public void setCover(GvImage image) {
        mAdapter.setCover(image);
        updateCover();
    }

    @Override
    public ImageChooser newImageChooser() {
        return mAdapter.newImageChooser();
    }

    @Override
    public <T extends GvDataParcelable> ReviewDataEditor<T> newDataEditor(GvDataType<T> dataType) {
        DataBuilderAdapter<T> adapter = mAdapter.getDataBuilderAdapter(dataType);
        return mEditorFactory.newEditor(adapter, getApp().getUi().getLauncher(), newImageChooser());
    }

    @Override
    public float getRating() {
        return mAdapter.getRating();
    }

    @Override
    public ReadyToBuildResult isReviewBuildable() {
        if(mAdapter.getSubject() == null || mAdapter.getSubject().length() == 0) {
            return ReadyToBuildResult.NoSubject;
        } else if(mAdapter.getDataBuilderAdapter(GvTag.TYPE).getGridData().size() == 0) {
                return ReadyToBuildResult.NoTags;
        } else {
            return ReadyToBuildResult.YES;
        }
    }

    @Override
    public GvDataList<GC> getGridViewData() {
        return super.getGridData();
    }

    @Override
    public Review buildReview() {
        Review review = mAdapter.buildReview();
        notifyBuildListeners();
        return review;
    }

    private void notifyBuildListeners() {
        for(BuildListener listener : mBuildListeners) {
            listener.onReviewBuilt();
        }
    }

    private void notifyModeListeners() {
        for(ModeListener listener : mModeListeners) {
            listener.onEditMode(mEditMode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGridItem.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void registerListener(BuildListener listener) {
        if(!mBuildListeners.contains(listener)) mBuildListeners.add(listener);
    }

    @Override
    public void unregisterListener(BuildListener listener) {
        if(mBuildListeners.contains(listener)) mBuildListeners.remove(listener);
    }

    @Override
    public void registerListener(ModeListener listener) {
        if(!mModeListeners.contains(listener)) mModeListeners.add(listener);
    }

    @Override
    public void unregisterListener(ModeListener listener) {
        if(mModeListeners.contains(listener)) mModeListeners.remove(listener);
    }

    @Override
    public void onClick() {
        setView();
        notifyModeListeners();
    }

    @Override
    public EditMode getEditMode() {
        return mEditMode;
    }

    private void setView() {
        mEditMode = mBannerButton.getEditMode();
        mAdapter.setView(mEditMode);
        mGridItem.setView(mEditMode);
    }
}
