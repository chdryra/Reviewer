package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdUrlConverter extends MdConverterBasic<DataUrl, MdUrlList.MdUrl, MdUrlList> {
    public MdUrlConverter() {
        super(MdUrlList.class);
    }

    @Override
    public MdUrlList.MdUrl convert(DataUrl datum) {
        ReviewId id = ReviewId.fromString(datum.getReviewId());
        return new MdUrlList.MdUrl(datum.getLabel(), datum.getUrl(), id);
    }
}
