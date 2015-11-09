package com.chdryra.android.reviewer.Model.ReviewData;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdCommentConverter extends MdConverterBasic<DataComment, MdCommentList.MdComment, MdCommentList> {

    public MdCommentConverter() {
        super(MdCommentList.class);
    }

    @Override
    public MdCommentList.MdComment convert(DataComment datum) {
        String comment = datum.getComment();
        boolean isHeadline = datum.isHeadline();
        String reviewId = datum.getReviewId();
        return new MdCommentList.MdComment(comment, isHeadline, ReviewId.fromString(reviewId));
    }
}
