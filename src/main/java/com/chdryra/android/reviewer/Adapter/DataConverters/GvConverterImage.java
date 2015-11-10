package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterImage extends GvConverterBasic<DataImage, GvImageList.GvImage, GvImageList> {

    public GvConverterImage() {
        super(GvImageList.class);
    }

    @Override
    public GvImageList.GvImage convert(DataImage datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvImageList.GvImage(id, datum.getBitmap(), datum.getDate(),
                datum.getCaption(), datum.isCover());
    }
}
