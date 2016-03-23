/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterComments extends MdConverterDataReview<DataComment, MdComment> {
    @Override
    public MdComment convert(DataComment datum, ReviewId reviewId) {
        String comment = datum.getComment();
        boolean isHeadline = datum.isHeadline();
        return new MdComment(newMdReviewId(reviewId), comment, isHeadline);
    }
}
