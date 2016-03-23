/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdDate;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdImage;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterImages extends MdConverterDataReview<DataImage, MdImage> {
    @Override
    public MdImage convert(DataImage datum, ReviewId reviewId) {
        MdReviewId id = newMdReviewId(reviewId);
        MdDate date = new MdDate(id, datum.getDate().getTime());
        return new MdImage(id, datum.getBitmap(), date, datum.getCaption(), datum.isCover());
    }
}
