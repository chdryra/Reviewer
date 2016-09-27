/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
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

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewEditorDefault<GC extends GvDataList<? extends GvDataParcelable>> extends ReviewViewDefault<GC>
        implements ReviewEditor<GC> {
    private final ReviewBuilderAdapter<?> mAdapter;
    private final FactoryReviewDataEditor mEditorFactory;

    public ReviewEditorDefault(ReviewBuilderAdapter<GC> adapter,
                               ReviewViewActions<GC> actions,
                               ReviewViewParams params,
                               FactoryReviewDataEditor editorFactory) {
        super(new ReviewViewPerspective<>(adapter, actions, params));
        mAdapter = adapter;
        mEditorFactory = editorFactory;
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
            getContainer().setRating(rating);
        }
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
    public void notifyBuilder() {
        mAdapter.notifyDataObservers();
    }

    @Override
    public ImageChooser getImageChooser() {
        return mAdapter.getImageChooser();
    }

    @Override
    public <T extends GvDataParcelable> ReviewDataEditor<T> newDataEditor(GvDataType<T> dataType) {
        DataBuilderAdapter<T> adapter = mAdapter.getDataBuilderAdapter(dataType);
        return mEditorFactory.newEditor(adapter, getImageChooser());
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
    public Review buildReview() {
        return mAdapter.buildReview();
    }
}
