package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthorList;

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
    public GvAuthor convert(DataAuthor datum, String reviewId) {
        return new GvAuthor(newId(reviewId), datum.getName(), datum.getUserId());
    }
}
