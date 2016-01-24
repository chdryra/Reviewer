/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterImages extends MdConverterDataReview<DataImage, MdImage> {
    @Override
    public MdImage convert(DataImage datum) {
        MdReviewId id = newMdReviewId(datum.getReviewId());
        MdDate date = new MdDate(id, datum.getDate().getTime());
        return new MdImage(id, datum.getBitmap(), date, datum.getCaption(), datum.isCover());
    }
}
