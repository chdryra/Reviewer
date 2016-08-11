/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRef;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewRefList;


/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviewReferences
        extends GvConverterBasic<ReviewReference, GvReviewRef, GvReviewRefList>
        implements DataConverter<ReviewReference, GvReviewRef, GvReviewRefList> {
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    public GvConverterReviewReferences(GvConverterComments converterComments,
                                       GvConverterLocations converterLocations) {
        super(GvReviewRefList.class);
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
    }

    @Override
    public GvReviewRef convert(ReviewReference review, ReviewId parentId) {
        return new GvReviewRef(review, mConverterComments, mConverterLocations);
    }

    @Override
    public GvReviewRef convert(ReviewReference review) {
        return convert(review, null);
    }

    @Override
    public GvReviewRefList convert(IdableList<? extends ReviewReference> data) {
        GvReviewRefList list = new GvReviewRefList(newId(data.getReviewId()));
        for(ReviewReference datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
