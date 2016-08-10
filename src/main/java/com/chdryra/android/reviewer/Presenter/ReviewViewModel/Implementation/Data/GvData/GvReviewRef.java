/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhReviewRef;

public class GvReviewRef extends GvDataBasic<GvReviewRef> implements DataReviewInfo {
    public static final GvDataType<GvReviewRef> TYPE =
            new GvDataType<>(GvReviewRef.class, "review");

    private ReviewReference mReference;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private VhReviewRef mViewHolder;

    public GvReviewRef() {
        super(GvReviewRef.TYPE);
    }

    public GvReviewRef(ReviewReference reference,
                       GvConverterComments converterComments,
                       GvConverterLocations converterLocations) {
        super(GvReviewRef.TYPE, new GvReviewId(reference.getReviewId()));
        mReference = reference;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    public ReviewReference getReference() {
        return mReference;
    }

    public void unbind() {
        if(mViewHolder != null && mViewHolder.isBoundTo(mReference)) {
            mViewHolder.unbindFromReference();
        }
    }

    public void setViewHolder(VhReviewRef viewHolder) {
        mViewHolder = viewHolder;
    }

    @Override
    public DataSubject getSubject() {
        return mReference.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReference.getRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mReference.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mReference.getPublishDate();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhReviewRef(mConverterComments, mConverterLocations);
    }

    @Override
    public boolean isValidForDisplay() {
        return getGvReviewId() != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return mReference.isValidReference();
    }

    @Nullable
    @Override
    public GvDataParcelable getParcelable() {
        return null;
    }

    @Override
    public String getStringSummary() {
        return getGvReviewId().getStringSummary();
    }
}
