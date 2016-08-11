/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrls extends GvConverterReviewData<DataUrl, GvUrl, GvUrlList, GvUrl.Reference> {
    public GvConverterUrls() {
        super(GvUrlList.class, GvUrl.Reference.TYPE);
    }

    @Override
    public GvUrl convert(DataUrl datum, ReviewId reviewId) {
        return new GvUrl(getGvReviewId(datum, reviewId), datum.getLabel(), datum.getUrl());
    }

    @Override
    protected GvUrl.Reference convertReference(ReviewItemReference<DataUrl> reference) {
        return new GvUrl.Reference(reference, this);
    }
}
