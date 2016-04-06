/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.Interfaces.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvData extends VerboseDataReview, ViewHolderData, Parcelable {
    GvDataType<? extends GvData> getGvDataType();

    GvReviewId getGvReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();

    @Override
    ReviewId getReviewId();

    @Override
    int describeContents();

    @Override
    void writeToParcel(Parcel dest, int flags);

    @Override
    boolean hasData(DataValidator dataValidator);

    @Override
    ViewHolder getViewHolder();

    @Override
    boolean isValidForDisplay();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
