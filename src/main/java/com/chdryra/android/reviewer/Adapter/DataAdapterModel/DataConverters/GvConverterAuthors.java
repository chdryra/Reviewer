package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthors extends
        GvConverterBasic<DataAuthor, GvAuthorList.GvAuthor, GvAuthorList> {

    public GvConverterAuthors() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthorList.GvAuthor convert(DataAuthor datum, String reviewId) {
        return new GvAuthorList.GvAuthor(newId(reviewId), datum.getName(), datum.getUserId());
    }
}
