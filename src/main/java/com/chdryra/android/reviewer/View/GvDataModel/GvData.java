/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 March, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvData extends ViewHolderData, Parcelable {
    //abstract methods
    //abstract
    GvDataType<? extends GvData> getGvDataType();

    String getStringSummary();

    GvReviewId getReviewId();

    boolean hasElements();

    boolean isCollection();

    //Overridden
    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
