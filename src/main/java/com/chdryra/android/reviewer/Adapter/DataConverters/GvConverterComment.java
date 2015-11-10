package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterComment extends GvConverterBasic<DataComment, GvCommentList.GvComment, GvCommentList> {

    public GvConverterComment() {
        super(GvCommentList.class);
    }

    @Override
    public GvCommentList.GvComment convert(DataComment datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvCommentList.GvComment(id, datum.getComment(), datum.isHeadline());
    }
}
