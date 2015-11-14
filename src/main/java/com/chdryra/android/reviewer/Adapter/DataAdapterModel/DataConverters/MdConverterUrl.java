package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdUrlList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterUrl extends MdConverterDataReview<DataUrl, MdUrlList.MdUrl, MdUrlList> {
    public MdConverterUrl() {
        super(MdUrlList.class);
    }

    @Override
    public MdUrlList.MdUrl convert(DataUrl datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        return new MdUrlList.MdUrl(id, datum.getLabel(), datum.getUrl());
    }
}
