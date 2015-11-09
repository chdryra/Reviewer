package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCommentConverter extends GvConverterBasic<DataComment, GvCommentList.GvComment, GvCommentList> {

    public GvCommentConverter() {
        super(GvCommentList.class);
    }

    @Override
    public GvCommentList.GvComment convert(DataComment datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvCommentList.GvComment(id, datum.getComment(), datum.isHeadline());
    }
}
