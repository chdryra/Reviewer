package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterImages extends MdConverterBasic<DataImage, MdImageList.MdImage, MdImageList> {

    public MdConverterImages() {
        super(MdImageList.class);
    }

    @Override
    public MdImageList.MdImage convert(DataImage datum) {
        MdReviewId id = new MdReviewId(datum.getReviewId());
        MdDate date = new MdDate(id, datum.getDate().getTime());
        return new MdImageList.MdImage(id, datum.getBitmap(), date,
                datum.getCaption(), datum.isCover());
    }
}
