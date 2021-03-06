/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.Data;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolderData;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface GvData extends VerboseDataReview, ViewHolderData {
    GvDataType<? extends GvData> getGvDataType();

    GvReviewId getGvReviewId();

    @Nullable
    GvDataParcelable getParcelable();
}
