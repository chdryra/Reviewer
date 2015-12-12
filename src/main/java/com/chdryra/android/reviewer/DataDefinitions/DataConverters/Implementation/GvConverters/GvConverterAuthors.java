package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvAuthor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvAuthorList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUserId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthors extends
        GvConverterBasic<DataAuthor, GvAuthor, GvAuthorList> {

    public GvConverterAuthors() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthor convert(DataAuthor datum, ReviewId reviewId) {
        GvReviewId gvReviewId = newId(reviewId);
        return new GvAuthor(gvReviewId, datum.getName(),
                new GvUserId(gvReviewId, datum.getUserId().toString()));
    }
}
