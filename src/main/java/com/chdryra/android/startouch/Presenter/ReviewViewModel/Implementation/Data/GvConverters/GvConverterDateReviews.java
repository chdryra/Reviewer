/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDateList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDateReviews extends GvConverterReviewData.RefDataList<DataDate, GvDate,
        GvDateList, GvDate.Reference> {

    public GvConverterDateReviews() {
        super(GvDateList.class, GvDate.Reference.TYPE);
    }

    @Override
    public GvDate convert(DataDate datum, @Nullable ReviewId reviewId) {
        return new GvDate(newId(reviewId), datum.getTime());
    }

    @Override
    protected GvDate.Reference convertReference(ReviewItemReference<DataDate> reference) {
        return new GvDate.Reference(reference, this);
    }
}
