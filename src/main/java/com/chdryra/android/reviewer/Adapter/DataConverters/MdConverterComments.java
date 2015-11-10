package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterComments extends MdConverterBasic<DataComment, MdCommentList.MdComment, MdCommentList> {

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
