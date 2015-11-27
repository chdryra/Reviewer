package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrls extends GvConverterDataReview<DataUrl, GvUrl, GvUrlList> {
    public GvConverterUrls() {
        super(GvUrlList.class);
    }

    @Override
    public GvUrl convert(DataUrl datum) {
        return new GvUrl(newId(datum.getReviewId()), datum.getLabel(), datum.getUrl());
    }
}
