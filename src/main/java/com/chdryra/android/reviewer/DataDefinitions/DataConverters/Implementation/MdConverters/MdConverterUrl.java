package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterUrl extends MdConverterDataReview<DataUrl, MdUrl> {
    @Override
    public MdUrl convert(DataUrl datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdUrl(id, datum.getLabel(), datum.getUrl());
    }
}
