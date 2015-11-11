package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrls extends GvConverterDataReview<DataUrl, GvUrlList.GvUrl, GvUrlList> {
    public GvConverterUrls() {
        super(GvUrlList.class);
    }

    @Override
    public GvUrlList.GvUrl convert(DataUrl datum) {
        return new GvUrlList.GvUrl(newId(datum.getReviewId()), datum.getLabel(), datum.getUrl());
    }
}
