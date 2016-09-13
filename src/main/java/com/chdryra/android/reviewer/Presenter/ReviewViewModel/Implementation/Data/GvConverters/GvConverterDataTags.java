/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDataTags extends GvConverterReviewData.RefDataList<DataTag, GvTag, GvTagList, GvTag.Reference> {
    public GvConverterDataTags() {
        super(GvTagList.class, GvTag.Reference.TYPE);
    }

    @Override
    public GvTag convert(DataTag datum, @Nullable ReviewId reviewId) {
        return new GvTag(getGvReviewId(datum, reviewId), datum.getTag());
    }

    @Override
    protected GvTag.Reference convertReference(ReviewItemReference<DataTag> reference) {
        return new GvTag.Reference(reference, this);
    }
}
