/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorId;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorIdList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthorIds extends
        GvConverterReviewData.RefDataList<DataAuthorId, GvAuthorId, GvAuthorIdList, GvAuthorId
                .Reference> {
    private final AuthorsRepo mRepo;

    public GvConverterAuthorIds(AuthorsRepo repo) {
        super(GvAuthorIdList.class, GvAuthorId.Reference.TYPE);
        mRepo = repo;
    }

    @Override
    public GvAuthorId convert(DataAuthorId datum, @Nullable ReviewId reviewId) {
        return new GvAuthorId(newId(reviewId), mRepo.getAuthor(datum));
    }

    @Override
    protected GvAuthorId.Reference convertReference(ReviewItemReference<DataAuthorId> reference) {
        return new GvAuthorId.Reference(reference, this);
    }
}
