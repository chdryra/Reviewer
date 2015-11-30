package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdComment;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterComments extends MdConverterDataReview<DataComment, MdComment> {
    @Override
    public MdComment convert(DataComment datum) {
        String comment = datum.getComment();
        boolean isHeadline = datum.isHeadline();
        String reviewId = datum.getReviewId();
        return new MdComment(new MdReviewId(reviewId), comment, isHeadline);
    }
}
