package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthorReview;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthors extends
        GvConverterBasic<DataAuthorReview, GvAuthorList.GvAuthor, GvAuthorList> {

    public GvConverterAuthors() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthorList.GvAuthor convert(DataAuthorReview datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvAuthorList.GvAuthor(id, datum.getName(), datum.getUserId());
    }
}
