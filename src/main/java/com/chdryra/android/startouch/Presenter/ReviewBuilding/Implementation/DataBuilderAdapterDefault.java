/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.graphics.Bitmap;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.DataValue;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SubscribableReference;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.DataObservable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterionList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .ReviewViewAdapterBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderAdapterDefault<T extends GvDataParcelable> extends ReviewViewAdapterBasic<T>
        implements DataBuilderAdapter<T>, DataObservable.DataObserver {

    private final GvDataType<T> mType;
    private final ReviewBuilderAdapter<?> mParentBuilder;
    private final SubscribableReference<Bitmap> mCover;
    private final SubscribableReference<String> mSubject;
    private final SubscribableReference<Float> mRating;

    public DataBuilderAdapterDefault(GvDataType<T> type, ReviewBuilderAdapter<?> parentBuilder) {
        mType = type;
        mParentBuilder = parentBuilder;
        mCover = new SubscribableReference<Bitmap>() {
            @Override
            protected void doDereferencing(DereferenceCallback<Bitmap> callback) {
                callback.onDereferenced(new DataValue<>(getCover().getBitmap()));
            }
        };

        mSubject = new SubscribableReference<String>() {
            @Override
            protected void doDereferencing(DereferenceCallback<String> callback) {
                callback.onDereferenced(new DataValue<>(getSubject()));
            }
        };
        mRating = new SubscribableReference<Float>() {
            @Override
            protected void doDereferencing(DereferenceCallback<Float> callback) {
                callback.onDereferenced(new DataValue<>(getRating()));
            }
        };

        getDataBuilder().registerObserver(this);
    }

    @Override
    public String getSubject() {
        return mParentBuilder.getSubject();
    }

    @Override
    public float getRating() {
        return mParentBuilder.getRating();
    }

    @Override
    public void setRating(float rating) {
        mParentBuilder.setRating(rating);
    }

    @Override
    public DataReference<String> getSubjectReference() {
        return mSubject;
    }

    @Override
    public DataReference<Float> getRatingReference() {
        return mRating;
    }

    @Override
    public ReviewNode buildPreview() {
        String subject = getReviewView().getContainerSubject();
        if (subject.length() == 0) subject = Strings.Placeholders.NO_SUBJECT;
        return mParentBuilder.buildPreview(subject,
                getReviewView().getContainerRating());
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    @Override
    public boolean isRatingAverage() {
        return getBuilder().isRatingAverage();
    }

    @Override
    public float getCriteriaAverage() {
        if (mType.equals(GvCriterion.TYPE)) {
            return ((GvCriterionList) getGridData()).getAverageRating();
        } else {
            return getBuilder().getAverageRating();
        }
    }

    @Override
    public boolean add(T datum) {
        DataBuilder.ConstraintResult res = getDataBuilder().add(datum);
        if (res == DataBuilder.ConstraintResult.PASSED) {
            return true;
        } else {
            if (res == DataBuilder.ConstraintResult.HAS_DATUM) {
                makeToastHasItem(datum);
            }
            return false;
        }
    }

    @Override
    public void delete(T datum) {
        getDataBuilder().delete(datum);
    }

    @Override
    public void deleteAll() {
        getDataBuilder().deleteAll();
    }

    @Override
    public void replace(T oldDatum, T newDatum) {
        DataBuilder.ConstraintResult res = getDataBuilder().replace(oldDatum, newDatum);
        if (res == DataBuilder.ConstraintResult.HAS_DATUM) makeToastHasItem(newDatum);
    }

    @Override
    public void commitData() {
        getDataBuilder().commitData();
    }

    @Override
    public void resetData() {
        getDataBuilder().resetData();
    }

    @Override
    public void setRatingIsAverage(boolean ratingIsAverage) {
        mParentBuilder.setRatingIsAverage(ratingIsAverage);
    }

    @Override
    public GvDataList<T> getGridData() {
        return getDataBuilder().getData();
    }

    @Override
    public void setSubject(String subject, boolean adjustTags) {
        mParentBuilder.setSubject(subject, adjustTags);
    }

    @Override
    public DataReference<Bitmap> getCoverReference() {
        return mCover;
    }

    @Override
    public GvImage getCover() {
        return mType.equals(GvImage.TYPE) ? ((GvImageList) getGridData()).getCovers().get(0)
                : mParentBuilder.getCover();
    }

    @Override
    protected void onAttach() {
        attach();
    }

    @Override
    protected void onDetach() {
        detach();
    }

    @Override
    protected void notifySubscribers() {
        super.notifySubscribers();
        mSubject.notifySubscribers();
        mRating.notifySubscribers();
    }

    void attach() {
        registerObserver(mParentBuilder);
    }

    void detach() {
        unregisterObserver(mParentBuilder);
    }

    private DataBuilder<T> getDataBuilder() {
        return getBuilder().getDataBuilder(mType);
    }

    private ReviewBuilder getBuilder() {
        return mParentBuilder.getBuilder();
    }

    private void makeToastHasItem(GvData datum) {
        String toast = Strings.Toasts.HAS_DATA + " " + datum.getGvDataType().getDatumName();
        ReviewView<?> reviewView = getReviewView();
        if (reviewView == null) reviewView = mParentBuilder.getReviewView();
        reviewView.getCurrentScreen().showToast(toast);
    }
}

