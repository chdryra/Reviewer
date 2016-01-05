package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;

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
