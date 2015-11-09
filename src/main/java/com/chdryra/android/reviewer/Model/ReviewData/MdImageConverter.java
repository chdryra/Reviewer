package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataImage;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdImageConverter extends MdConverterBasic<DataImage, MdImageList.MdImage, MdImageList> {

    public MdImageConverter() {
        super(MdImageList.class);
    }

    @Override
    public MdImageList.MdImage convert(DataImage datum) {
        ReviewId id = ReviewId.fromString(datum.getReviewId());
        return new MdImageList.MdImage(datum.getBitmap(), datum.getDate(),
                datum.getCaption(), datum.isCover(), id);
    }
}
