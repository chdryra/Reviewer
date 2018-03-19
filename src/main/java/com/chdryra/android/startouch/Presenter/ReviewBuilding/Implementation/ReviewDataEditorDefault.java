/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataReferenceWrapper;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewDefault;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditorDefault<T extends GvDataParcelable> extends ReviewViewDefault<T>
        implements ReviewDataEditor<T> {
    private final DataBuilderAdapter<T> mBuilder;

    private final DataReferenceWrapper<String> mSubject;
    private final DataReferenceWrapper<Float> mRating;
    private boolean mRatingIsAverage;

    public ReviewDataEditorDefault(DataBuilderAdapter<T> builder,
                                   ReviewViewActions<T> actions,
                                   ReviewViewParams params) {
        super(new ReviewViewPerspective<>(builder, actions, params));
        mBuilder = builder;
        mRatingIsAverage = builder.isRatingAverage();
        mSubject = new DataReferenceWrapper<>(builder.getSubject());
        mRating = new DataReferenceWrapper<>(builder.getRating());
    }

    @Override
    public void setSubject() {
        mSubject.setData(getContainerSubject());
    }

    @Override
    public void setRatingIsAverage(boolean isAverage) {
        mRatingIsAverage = isAverage;
        update();
    }

    @Override
    public void setRating(float rating, boolean fromUser) {
        mRating.setData(rating);
        if (fromUser) setRatingIsAverage(false);
        onDataChanged();
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
    public GvImage getCoverImage() {
        return mBuilder.getCover();
    }

    @Override
    public void commitEdits(boolean adjustTags) {
        mBuilder.setSubject(getContainerSubject(), adjustTags);
        mBuilder.setRatingIsAverage(mRatingIsAverage);
        mBuilder.setRating(mRatingIsAverage ? mBuilder.getCriteriaAverage() : getContainerRating());
        commitData();
    }

    @Override
    public void discardEdits() {
        mBuilder.resetData();
    }

    @Override
    public DataReference<String> getEditorSubject() {
        return mSubject;
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
        detachPerspective();
    }

    @Override
    public void onDataChanged() {
        update();
    }

    @Override
    public void update() {
        updateRating();
        notifyDataObservers();
        mSubject.notifySubscribers();
        mRating.notifySubscribers();
    }

    private void updateRating() {
        if (mRatingIsAverage) setRating(mBuilder.getCriteriaAverage(), false);
    }
}
