package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterComments extends GvConverterDataReview<DataComment, GvComment, GvCommentList> {

    public GvConverterComments() {
        super(GvCommentList.class);
    }

    @Override
    public GvComment convert(DataComment datum) {
        return new GvComment(newId(datum.getReviewId()),
                datum.getComment(), datum.isHeadline());
    }
}
