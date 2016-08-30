/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataBasic<T extends GvData> implements GvData {
    protected GvDataType<T> mType;
    protected GvReviewId mReviewId;

    protected GvDataBasic(GvDataType<T> type) {
        mType = type;
    }

    protected GvDataBasic(GvDataType<T> type, @Nullable GvReviewId reviewId) {
        mType = type;
        mReviewId = reviewId;
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvDataParcelableBasic)) return false;

        GvDataParcelableBasic<?> that = (GvDataParcelableBasic<?>) o;

        if (!mType.equals(that.mType)) return false;
        return !(mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null);

    }

    @Override
    public boolean isVerboseCollection() {
        return false;
    }

    @Override
    public boolean hasElements() {
        return false;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public GvDataType<T> getGvDataType() {
        return mType;
    }

    //Overridden
    @Override
    public GvReviewId getGvReviewId() {
        return mReviewId;
    }
}
