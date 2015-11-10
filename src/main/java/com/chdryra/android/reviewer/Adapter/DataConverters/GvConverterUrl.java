package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataUrl;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterUrl extends GvConverterBasic<DataUrl, GvUrlList.GvUrl, GvUrlList> {
    public GvConverterUrl() {
        super(GvUrlList.class);
    }

    @Override
    public GvUrlList.GvUrl convert(DataUrl datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvUrlList.GvUrl(id, datum.getLabel(), datum.getUrl());
    }
}
