/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorName;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthors extends GvConverterBasic<AuthorName, GvAuthorName, GvAuthorList> {

    public GvConverterAuthors() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthorName convert(AuthorName datum, @Nullable ReviewId reviewId) {
        GvReviewId gvReviewId = newId(reviewId);
        return new GvAuthorName(gvReviewId, datum.getName(), new GvAuthorId(datum.getAuthorId()
                .toString()));
    }
}
