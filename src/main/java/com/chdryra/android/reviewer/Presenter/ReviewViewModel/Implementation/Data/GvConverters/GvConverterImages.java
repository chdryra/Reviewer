package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImageList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterImages extends GvConverterDataReview<DataImage, GvImage, GvImageList> {

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
