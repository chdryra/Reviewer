package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterImages extends GvConverterDataReview<DataImage, GvImageList.GvImage, GvImageList> implements GvImageConverter{
    private DataConverter<DataDate, GvDateList.GvDate, ?> mConverter;

    public GvConverterImages(DataConverter<DataDate, GvDateList.GvDate, ?> converter) {
        super(GvImageList.class);
        mConverter = converter;
    }

    @Override
    public GvImageList.GvImage convert(DataImage datum) {
        GvReviewId id = newId(datum.getReviewId());
        GvDateList.GvDate gvDate = mConverter.convert(datum.getDate());
        return new GvImageList.GvImage(id, datum.getBitmap(), gvDate, datum.getCaption(),
                datum.isCover());
    }
}
