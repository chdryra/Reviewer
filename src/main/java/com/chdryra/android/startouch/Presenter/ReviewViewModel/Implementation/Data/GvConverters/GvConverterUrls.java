/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrls extends GvConverterReviewData.RefDataList<DataUrl, GvUrl, GvUrlList, GvUrl.Reference> {
    public GvConverterUrls() {
        super(GvUrlList.class, GvUrl.Reference.TYPE);
    }

    @Override
    public GvUrl convert(DataUrl datum, @Nullable ReviewId reviewId) {
        return new GvUrl(newId(reviewId), datum.getLabel(), datum.getUrl());
    }

    @Override
    protected GvUrl.Reference convertReference(ReviewItemReference<DataUrl> reference) {
        return new GvUrl.Reference(reference, this);
    }
}
