package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.GvImageConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterImages extends GvConverterDataReview<DataImage, GvImage, GvImageList> implements GvImageConverter {

    private DataConverter<DataDate, GvDate, ?> mConverter;

    public GvConverterImages(DataConverter<DataDate, GvDate, ?> converter) {
        super(GvImageList.class);
        mConverter = converter;
    }

    @Override
    public GvImage convert(DataImage datum) {
        GvReviewId id = newId(datum.getReviewId());
        GvDate gvDate = mConverter.convert(datum.getDate());
        return new GvImage(id, datum.getBitmap(), gvDate, datum.getCaption(),
                datum.isCover());
    }
}
