package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterAuthor extends
        GvConverterBasic<DataAuthor, GvAuthorList.GvAuthor, GvAuthorList> {

    public GvConverterAuthor() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthorList.GvAuthor convert(DataAuthor datum) {
        return new GvAuthorList.GvAuthor(datum.getName(), datum.getUserId());
    }
}
