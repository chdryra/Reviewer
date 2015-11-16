package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterComments extends MdConverterDataReview<DataComment, MdCommentList.MdComment, MdCommentList> {

    public MdConverterComments() {
        super(MdCommentList.class);
    }

    @Override
    public MdCommentList.MdComment convert(DataComment datum) {
        String comment = datum.getComment();
        boolean isHeadline = datum.isHeadline();
        String reviewId = datum.getReviewId();
        return new MdCommentList.MdComment(new MdReviewId(reviewId), comment, isHeadline);
    }
}
