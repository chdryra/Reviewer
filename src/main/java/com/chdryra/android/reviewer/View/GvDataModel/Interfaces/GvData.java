/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel.Interfaces;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvData extends VerboseDataReview, ViewHolderData, Parcelable {
    //abstract
    GvDataType<? extends GvData> getGvDataType();

    GvReviewId getGvReviewId();

    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();

    @Override
    String getReviewId();

    //Overridden
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
