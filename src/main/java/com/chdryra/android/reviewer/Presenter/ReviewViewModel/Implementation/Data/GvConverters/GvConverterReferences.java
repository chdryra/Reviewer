/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataRefList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterReferences<Value extends HasReviewId,
        GvRef extends GvDataRef<GvRef, ? extends Value, ?>, Reference extends ReviewItemReference<Value>>
        extends GvConverterBasic<Reference, GvRef, GvDataRefList<GvRef>> {

    public GvConverterReferences(GvDataType<GvRef> dataType) {
        super(dataType);
    }

    @Override
    protected GvDataRefList<GvRef> newList(ReviewId reviewId) {
        GvReviewId id = new GvReviewId(reviewId);
        return new GvDataRefList<>(getOutputType(), id);
    }
}
