/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewDefault;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditorDefault<T extends GvDataParcelable> extends ReviewViewDefault<T>
        implements ReviewDataEditor<T> {
    private String mSubject;
    private float mRating;
    private boolean mRatingIsAverage;

    private final DataBuilderAdapter<T> mBuilder;

    public ReviewDataEditorDefault(DataBuilderAdapter<T> builder,
                                   ReviewViewActions<T> actions,
                                   ReviewViewParams params) {
        super(new ReviewViewPerspective<>(builder, actions, params));
        mBuilder = builder;
        mSubject = builder.getSubject();
        mRating = builder.getRating();
        mRatingIsAverage = builder.isRatingAverage();
    }

    @Override
    public void setSubject() {
        mSubject = getContainerSubject();
    }

    @Override
    public boolean isRatingAverage() {
        return mRatingIsAverage;
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
        update();
    }

    private void updateRating() {
        if(mRatingIsAverage) setRating(mBuilder.getCriteriaAverage(), false);
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        mRating = rating;
        if(fromUser) {
            setRatingIsAverage(false);
        } else if(getContainer() != null) {
            getContainer().setRating(mRating);
        }
    }

    @Override
    public boolean add(T datum) {
        return mBuilder.add(datum);
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        mBuilder.replace(oldDatum, newDatum);
    }

    @Override
    public void delete(T datum) {
        mBuilder.delete(datum);
    }

    @Override
    public GvImage getCover() {
        return mBuilder.getCover();
    }

    @Override
    public void commitEdits() {
        mBuilder.setSubject(getContainerSubject());
        mBuilder.setRatingIsAverage(mRatingIsAverage);
        mBuilder.setRating(mRatingIsAverage ? mBuilder.getCriteriaAverage() : getContainerRating());
        commitData();
    }

    @Override
    public void discardEdits() {
        mBuilder.resetData();
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public void resetData() {
        mBuilder.resetData();
    }

    @Override
    public void commitData() {
        mBuilder.commitData();
    }

    @Override
    public void detachFromBuilder() {
        detachFromAdapter();
    }

    @Override
    public void update() {
        updateRating();
        notifyDataObservers();
    }
}
