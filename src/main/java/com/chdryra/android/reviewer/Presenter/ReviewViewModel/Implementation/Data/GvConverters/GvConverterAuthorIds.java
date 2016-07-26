/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthorIdList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthorIds extends
        GvConverterBasic<DataAuthorId, GvAuthorId, GvAuthorIdList> {

    public GvConverterAuthorIds() {
        super(GvAuthorIdList.class);
    }

    @Override
    public GvAuthorId convert(DataAuthorId datum, ReviewId reviewId) {
        GvReviewId gvReviewId = getGvReviewId(datum, reviewId);
        return new GvAuthorId(gvReviewId, datum.toString());
    }
}
